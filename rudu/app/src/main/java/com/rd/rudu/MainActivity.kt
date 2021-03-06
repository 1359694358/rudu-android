package com.rd.rudu

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.app.DefaultApp
import com.google.android.app.widget.BaseActivity
import com.permissionx.guolindev.PermissionX
import com.rd.rudu.databinding.ActivityMainBinding
import com.google.android.app.databinding.SimpleDialogBinding
import com.rd.rudu.ui.activity.HomeActivity
import com.google.android.app.widget.*
import com.google.android.app.utils.PermissionPageUtils
import com.google.android.app.utils.ToastUtil
import com.rd.rudu.ui.activity.GuideActivity
import com.rd.rudu.ui.activity.LoginActivity
import com.rd.rudu.ui.fragment.GuideFragment
import com.rd.rudu.utils.PrivacyUtils
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestAppPermission()
    }
    fun requestAppPermission()
    {
        var sdCard= listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        PermissionX.init(this)
                .permissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .onExplainRequestReason { scope, deniedList ->
                    if(deniedList.containsAll(sdCard))
                    {
                        var simpleDialogBinding=SimpleDialogBinding.inflate(layoutInflater)
                        simpleDialogBinding.setSubTitle("${resources.getString(R.string.app_name)}需要读取您的存储卡，以便于数据存储")
                        simpleDialogBinding.setCancelClickListener {
                            ToastUtil.show(this,"没有取到权限")
                        }
                        simpleDialogBinding.setSureClickListener {
                            requestAppPermission()
                        }
                        simpleDialogBinding.show(window.decorView as ViewGroup)
                    }
                    else
                    {
                        if(PrivacyUtils.showPrivacy(this,::init))
                            init()
                    }

                }
                .onForwardToSettings { _, deniedList ->
                    if(deniedList.containsAll(sdCard))
                    {
                        var simpleDialogBinding=SimpleDialogBinding.inflate(layoutInflater)
                        simpleDialogBinding.setSubTitle("您已禁止${resources.getString(R.string.app_name)}访问访问存储权限。可以点击确认进入设置页面，授予对应权限")
                        simpleDialogBinding.setCancelClickListener {
                            ToastUtil.show(this,"没有取到权限")
                        }
                        simpleDialogBinding.setSureClickListener {
                            var permissionPage= PermissionPageUtils(this)
                            permissionPage.jumpPermissionPage()
                        }
                        simpleDialogBinding.show(window.decorView as ViewGroup)
                    }
                    else
                    {
                        if(PrivacyUtils.showPrivacy(this,::init))
                            init()
                    }

                }
                .request { allGranted, grantedList, _ ->
                    if(allGranted||grantedList.containsAll(sdCard))
                    {
                        if(PrivacyUtils.showPrivacy(this,::init))
                            init()
                    }
                    else
                    {
                        var simpleDialogBinding= SimpleDialogBinding.inflate(layoutInflater)
                        simpleDialogBinding.setSubTitle("您已禁止${resources.getString(R.string.app_name)}授权权限。可以点击确认进入设置页面，授予对应权限")
                        simpleDialogBinding.setCancelClickListener {
                            killProcess()
                        }
                        simpleDialogBinding.setSureClickListener {
                            var permissionPage= PermissionPageUtils(this)
                            permissionPage.jumpPermissionPage()
                        }
                        simpleDialogBinding.show(window.decorView as ViewGroup)
                    }
                }
    }
    private fun init()
    {
        DefaultApp.initFile(this)
        startHomeActivity()
    }

    fun startHomeActivity()
    {
        if(GuideActivity.needWatchGuide())
        {
            window.decorView.id= View.generateViewId();
            var id=window.decorView.id
            supportFragmentManager.beginTransaction().replace(id,GuideFragment()).commitAllowingStateLoss()
        }
        else
        {
            contentBinding.root.postDelayed({
                if(GuideActivity.needWatchGuide())
                {
//                startActivity<GuideActivity>()
                }
                else
                {
                    startActivity<HomeActivity>()
                    finish()
                }
//            startActivity<HomeActivity>()
            },2000)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        killProcess()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onDestroy() {
        window?.setBackgroundDrawable(null)
        super.onDestroy()
    }

    override fun getFitSystemWindow(): Boolean {
        return false
    }
}