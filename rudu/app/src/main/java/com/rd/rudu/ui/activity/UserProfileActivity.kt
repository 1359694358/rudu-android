package com.rd.rudu.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.lifecycle.Observer
import com.google.android.app.utils.ToastUtil
import com.google.android.app.utils.ViewUtils
import com.google.android.app.utils.imageloader.ImageLoader
import com.google.android.app.widget.BaseActivity
import com.google.android.app.widget.datepicker.CustomDatePicker
import com.google.android.app.widget.datepicker.DateFormatUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetListItemModel
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.rd.rudu.R
import com.rd.rudu.bean.result.LoginResultBean
import com.rd.rudu.databinding.ActivityPersonalInfoBinding
import com.rd.rudu.utils.GlideEngine
import com.rd.rudu.vm.UserViewModel
import java.io.File

class UserProfileActivity: BaseActivity<ActivityPersonalInfoBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_personal_info
    }

    val userViewModel:UserViewModel by lazy { getViewModelByApplication<UserViewModel>() }
    var loadingDialog: QMUITipDialog?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        title = "个人信息"
        setMoreText("保存")
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.moreText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
        contentBinding.personalInfoLayout.tvEditDetailRightBirthday.setOnClickListener {
            // 日期格式为yyyy-MM-dd
            var currentDate=(it as TextView).text.toString()
            if(currentDate.isEmpty())
            {
                currentDate=DateFormatUtils.long2Str(System.currentTimeMillis(), false)
            }
            mDatePicker.show(currentDate)
        }
        initDatePicker()
        contentBinding.avatarLayout.personalInfoAvatarImg.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage()
    {
        var photoSPan= SpannableStringBuilder("拍摄")
        photoSPan[0,photoSPan.length]= ForegroundColorSpan(ContextCompat.getColor(this,R.color.qmuibottomsheettxtcolor))
        var takePhoto= QMUIBottomSheetListItemModel(photoSPan,"")
        var uploadSpan= SpannableStringBuilder("上传")
        uploadSpan[0,uploadSpan.length]= ForegroundColorSpan(ContextCompat.getColor(this,R.color.qmuibottomsheettxtcolor))
        var upload= QMUIBottomSheetListItemModel(uploadSpan,"")
        var bottomsheet= QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .setAddCancelBtn(true)
                .setTitle("请选择上传方式")
                .addItem(takePhoto)
                .addItem(upload)
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    dialog.dismiss()
                    if(position==0)
                    {
                        openCamera()
                    }
                    else if(position==1)
                    {
                        openAlbum()
                    }
                }
                .build()
        bottomsheet.show()
    }

    fun openAlbum()
    {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .compressQuality(80)
                .isCompress(true)
                .minimumCompressSize(500)
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: List<LocalMedia?>) {
                        // 结果回调
                        if(result.isNotEmpty())
                        {
                            result[0]?.let {
                                uploadImage(File(it.realPath))
                            }
                        }
                    }

                    override fun onCancel() {
                        // 取消
                    }
                })
    }

    fun openCamera()
    {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .compressQuality(80)
                .isCompress(true)
                .minimumCompressSize(500)
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: List<LocalMedia?>) {
                        // 结果回调
                        if(result.isNotEmpty())
                        {
                            result[0]?.let {
                                uploadImage(File(it.realPath))
                            }
                        }
                    }

                    override fun onCancel() {
                        // 取消
                    }
                })
    }

    private fun uploadImage(file: File)
    {
        loadingDialog = QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("头像上传中")
                .create()
        loadingDialog?.show()
        userViewModel.changeImage(file)

        userViewModel.changeAvatarObserver.observe(this, Observer {
            loadingDialog?.dismiss()
            if(it?.yes()==true)
            {
                var userInfo=LoginResultBean.LoginResult.getLoginResult()
                userInfo.avatar=it.data
                LoginResultBean.LoginResult.setLoginResult(userInfo)
                refreshUserInfo()
                ToastUtil.show(this,"上传头像成功")
            }
            else
            {
                ToastUtil.show(this,"上传头像失败")
            }
        })
    }
    private fun refreshUserInfo()
    {
        var loginResult= LoginResultBean.LoginResult.getLoginResult()
        contentBinding.personalInfoLayout.tvEditDetailRightId.text=loginResult.id
        contentBinding.personalInfoLayout.tvEditDetailRightNickname.setText(loginResult.nickName)
        if(loginResult.birthday?.isNotEmpty()==true)
            contentBinding.personalInfoLayout.tvEditDetailRightBirthday.text = DateFormatUtils.long2Str(DateFormatUtils.str2Long(loginResult.birthday, false), false)
        ImageLoader.loader.load(contentBinding.avatarLayout.personalInfoAvatarImg,"${loginResult.avatar}", ContextCompat.getDrawable(this,R.mipmap.morentouxiang))
        if(loginResult.gender=="1")
        {
            contentBinding.personalInfoLayout.personalInfoSexMale.isChecked=true
        }
        else if(loginResult.gender=="0")
        {
            contentBinding.personalInfoLayout.personalInfoSexMale.isChecked=true
        }
    }

    override fun moreClick()
    {
        super.moreClick()
        if(contentBinding.personalInfoLayout.tvEditDetailRightNickname.text.toString().isEmpty())
        {
            ToastUtil.show(this,"昵称不能为空")
            return
        }
        if(contentBinding.personalInfoLayout.tvEditDetailRightBirthday.text.toString().isEmpty())
        {
            ToastUtil.show(this,"出生日期不能为空")
            return
        }
        if(!contentBinding.personalInfoLayout.personalInfoSexMale.isChecked&&!contentBinding.personalInfoLayout.personalInfoSexFemale.isChecked)
        {
            ToastUtil.show(this,"请选择性别")
            return
        }
        var loginResult= LoginResultBean.LoginResult.getLoginResult()
        var gender=if(contentBinding.personalInfoLayout.personalInfoSexMale.isChecked) "0" else "1"
        userViewModel.saveUserInfo(loginResult.id,contentBinding.personalInfoLayout.tvEditDetailRightNickname.text.toString(),gender,contentBinding.personalInfoLayout.tvEditDetailRightBirthday.text.toString(),loginResult.avatar)
    }
    override fun onResume() {
        super.onResume()
        refreshUserInfo()
    }

    lateinit var mDatePicker:CustomDatePicker
    fun initDatePicker()
    {
        val beginTimestamp:Long = DateFormatUtils.str2Long("1900-01-01", false)
        val endTimestamp = System.currentTimeMillis()
        // 通过时间戳初始化日期，毫秒级别

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = CustomDatePicker(this,
                CustomDatePicker.Callback { timestamp ->
                    contentBinding.personalInfoLayout.tvEditDetailRightBirthday.setText(DateFormatUtils.long2Str(timestamp, false))
                },
                beginTimestamp,
                endTimestamp)
        // 不允许点击屏幕或物理返回键关闭
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false)
        // 不显示时和分
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false)
        // 不允许循环滚动
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false)
        // 不允许滚动动画
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDatePicker.onDestroy()
    }
}