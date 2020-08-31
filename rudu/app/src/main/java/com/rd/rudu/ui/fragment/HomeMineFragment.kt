package com.rd.rudu.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.utils.imageloader.ImageLoader
import com.google.android.app.widget.BaseFragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.rd.rudu.R
import com.rd.rudu.bean.result.LoginResultBean
import com.rd.rudu.databinding.FragmentHomemineBinding
import com.rd.rudu.ui.activity.LoginActivity
import com.rd.rudu.utils.GlideEngine
import com.rd.rudu.utils.clearLoginState
import com.rd.rudu.vm.UserViewModel
import org.jetbrains.anko.support.v4.startActivity
import java.io.File


class HomeMineFragment: BaseFragment<FragmentHomemineBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_homemine
    }
    val userViewModel:UserViewModel by lazy { getViewModelByApplication(UserViewModel::class.java) }
    var loadingDialog: QMUITipDialog?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding.titleBar.viewTreeObserver.addOnDrawListener {
            if(contentBinding.titleBar.layoutParams!=null&&contentBinding.titleBar.layoutParams is ViewGroup.MarginLayoutParams)
            {
                (contentBinding.titleBar.layoutParams as ViewGroup.MarginLayoutParams).topMargin= StatusBarUtil.getStatusBarHeight(requireActivity())
                contentBinding.titleBar.requestLayout()
            }
        }
        contentBinding.userIcon.setOnClickListener {
            var loginResult=LoginResultBean.LoginResult.getLoginResult()
            if(loginResult.isLogin)
            {
                var bottomsheet= QMUIBottomSheet.BottomListSheetBuilder(requireActivity())
                        .setGravityCenter(true)
                        .setAddCancelBtn(true)
                        .setTitle("请选择上传方式")
                        .addItem("拍摄")
                        .addItem("上传")
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
            else
            {
                startActivity<LoginActivity>()
            }
        }

        contentBinding.exitLogin.setOnClickListener {
            clearLoginState()
            refreshUserInfo()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshUserInfo()
    }
    fun refreshUserInfo()
    {
        var loginResult=LoginResultBean.LoginResult.getLoginResult()
        if(loginResult.isLogin)
        {
            contentBinding.exitLogin.visibility=View.VISIBLE
            contentBinding.nickName.text="手机号：${loginResult.telephone}"
            ImageLoader.loader.load(contentBinding.userIcon,loginResult.avatar,ContextCompat.getDrawable(requireActivity(),R.mipmap.morentouxiang))
        }
        else
        {
            contentBinding.exitLogin.visibility=View.GONE
            contentBinding.nickName.setText(R.string.app_nologin_nick)
            contentBinding.userIcon.setImageResource(R.mipmap.morentouxiang)
        }
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
                                uploadImage(File(it.path))
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
                                uploadImage(File(it.path))
                            }
                        }
                    }

                    override fun onCancel() {
                        // 取消
                    }
                })
    }

    private fun uploadImage(file:File)
    {
        loadingDialog = QMUITipDialog.Builder(requireContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("头像上传中")
                .create()
        loadingDialog?.show()
        userViewModel.changeImage(file)
    }
}