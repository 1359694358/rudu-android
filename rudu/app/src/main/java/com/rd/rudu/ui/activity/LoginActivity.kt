package com.rd.rudu.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.view.View
import androidx.core.text.set
import androidx.lifecycle.Observer
import com.google.android.app.utils.*
import com.google.android.app.widget.BaseActivity
import com.qmuiteam.qmui.span.QMUITouchableSpan
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.rd.rudu.R
import com.rd.rudu.bean.request.LoginEntity
import com.rd.rudu.bean.request.LoginType
import com.rd.rudu.bean.result.LoginResultBean
import com.rd.rudu.databinding.ActivityLoginlayoutBinding
import com.rd.rudu.utils.saveAppToken
import com.rd.rudu.vm.UserViewModel

class LoginActivity: BaseActivity<ActivityLoginlayoutBinding>()
{
    lateinit var countDownTimer: CountDown
    val userViewModel: UserViewModel by lazy { getViewModelByApplication(UserViewModel::class.java) }
    private var loginType= LoginType.Mobile
    private var verKey:String=""
    private var weChatId:String=""
    private var alipayId:String=""
    private var phone=""
    override fun getLayoutResId(): Int {
        return R.layout.activity_loginlayout
    }

    override fun getStatusBarColor(): Int {
        return Color.TRANSPARENT
    }

    override fun getFitSystemWindow(): Boolean {
        return false
    }

    override fun setStatusBarMode() {
        StatusBarUtil.setDarkMode(this)
    }

    var socialLoginEntity: LoginEntity?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var agreeSpan=SpannableStringBuilder(resources.getString(R.string.agree_str))
        var star=agreeSpan.length-8
        var end=agreeSpan.length
        agreeSpan[star,end]= ClickTouchSpan()
        contentBinding.appAgree.setLinkUnderLineColor(Color.WHITE)
        contentBinding.appAgree.setLinkUnderLineHeight(2)
        contentBinding.appAgree.text=agreeSpan
        addLoginListener()
        countDownTimer = CountDown(60 * 1000)

        toolbarBinding?.root?.setBackgroundColor(Color.TRANSPARENT)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,Color.WHITE,R.mipmap.icon_back_b))
    }
    var loadingDialog:QMUITipDialog?=null
    fun addLoginListener()
    {
        contentBinding.getSmsCode.setOnClickListener {
            var phone=contentBinding.phoneInput.text.toString()
            if(!Utility.netstatusOk(this))
            {
                ToastUtil.show(this,"请检查你的网络")
                return@setOnClickListener
            }
            if(!Utility.isMobileNO(phone))
            {
                ToastUtil.show(it.context,"请输入正确的手机号")
                return@setOnClickListener
            }
            invokeGetInvalidataNum()
        }
        contentBinding.loginButton.setOnClickListener {
            if(!Utility.netstatusOk(this))
            {
                ToastUtil.show(this,"请检查你的网络")
                return@setOnClickListener
            }
            if(!Utility.isMobileNO(contentBinding.phoneInput.text.toString()))
            {
                ToastUtil.show(it.context,"请输入正确的手机号")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(contentBinding.smsCodeInput.text.toString()))
            {
                ToastUtil.show(it.context,"请输入验证码")
                return@setOnClickListener
            }
            loginType=LoginType.Mobile
            phone=contentBinding.phoneInput.text.toString();
            var verCode=contentBinding.smsCodeInput.text.toString()
            loadingDialog = QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在登录")
                .create()
            loadingDialog?.show()
            userViewModel.login(resources.getString(R.string.youzan_clientId),phone,"",loginType,phone,verKey,verCode,weChatId,alipayId)
        }

        userViewModel.loginObserver.observe(this, Observer
        {
            loadingDialog?.dismiss()
            if(it?.yes()==true)
            {
                if (it?.code==40001&&socialLoginEntity!=null)
                {
                    finish()
                }
                else
                {
                    logd("登录成功")
                    LoginResultBean.LoginResult.setLoginResult(it.data)
                    saveAppToken(it.token)
                    finish()
                }
            }
            else
            {
                ToastUtil.show(this,"登录失败 ${it?.msg?:""}")
            }
        })
        userViewModel.smsCodeObserver.observe(this, Observer{
            if(it?.yes()==true)
            {
                countDownTimer.start()
                verKey=it.data.verKey
                ToastUtil.show(this,"验证码已发送")
            }
            else
            {
                ToastUtil.show(this,"验证码发送失败 ${it?.msg?:""}")
                resetGetInvalidataBtn()
            }
        })

    }
    inner class ClickTouchSpan: QMUITouchableSpan(Color.WHITE,Color.WHITE,Color.TRANSPARENT,Color.TRANSPARENT)
    {
        init {
            setIsNeedUnderline(true)
        }
        override fun onSpanClick(widget: View?)
        {
            var use_terms=resources.getString(R.string.use_terms)
            var cacheDir=cacheDir.absolutePath
            var path="${cacheDir}${use_terms}"
            OfficeFileViewActivity.startActivity(this@LoginActivity,path,"用户使用条款")
        }

    }


    inner class CountDown(millisInFuture: Long) : CountDownTimer(millisInFuture, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            val remain = (millisUntilFinished / 1000).toInt()
            val label = resources.getString(R.string.reget)
            contentBinding.getSmsCode.text = remain.toString() + label
        }

        override fun onFinish() {
            resetGetInvalidataBtn()
        }

    }

    override fun onBackPressed() {
        userViewModel.youzanTokenObserver.postValue(null)
        super.onBackPressed()
    }

    override fun backHandle() {
        userViewModel.youzanTokenObserver.postValue(null)
        super.backHandle()
    }

    fun invokeGetInvalidataNum()
    {
        hideKeyBroad()
        contentBinding.getSmsCode.text = "发送中"
        contentBinding.getSmsCode.isClickable = false
        userViewModel.getSmsCode(contentBinding.phoneInput.text.toString())
//        countDownTimer.start()
    }

    fun resetGetInvalidataBtn()
    {
        contentBinding.getSmsCode.setText(R.string.getvalidatenum)
        if (Utility.isMobileNO(contentBinding.phoneInput.text.toString()))
        {
            contentBinding.getSmsCode.isEnabled = true
            contentBinding.getSmsCode.isClickable = true
        }
    }
}
