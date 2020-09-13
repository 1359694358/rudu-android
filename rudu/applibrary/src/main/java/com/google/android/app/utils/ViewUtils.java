package com.google.android.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringDef;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class ViewUtils
{

    public static final String LAYOUT = "layout";
    public static final String DRAWABLE = "drawable";
    public static final String MIPMAP = "mipmap";
    public static final String MENU = "menu";
    public static final String RAW = "raw";
    public static final String ANIM = "anim";
    public static final String STRING = "string";
    public static final String STYLE = "style";
    public static final String STYLEABLE = "styleable";
    public static final String INTEGER = "integer";
    public static final String ID = "id";
    public static final String DIMEN = "dimen";
    public static final String COLOR = "color";
    public static final String BOOL = "bool";
    public static final String ATTR = "attr";

    @StringDef({LAYOUT,DRAWABLE,MIPMAP,MENU,RAW,ANIM,STRING,STYLE,STYLEABLE,INTEGER,ID,DIMEN,COLOR,BOOL,ATTR})
    @Target({PARAMETER,FIELD})
    @Retention(RUNTIME)
    public @interface AndroidResType
    {

    }


    public static Drawable tintDrawable(int color, @DrawableRes int drawableResId,Context context)
    {
        return tintDrawable(color,ContextCompat.getDrawable(context,drawableResId));
    }

    public static <T extends Drawable> T tintDrawable(int color, Drawable drawable) {
        Drawable newDrawable = DrawableCompat.wrap(drawable);
        setBitmapDrawableTintColor(color, newDrawable);
        newDrawable = newDrawable.mutate();
        newDrawable.invalidateSelf();
        return (T) newDrawable;
    }

    public static Drawable setDrawableColor(int color,Drawable drawable)
    {
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        drawable.invalidateSelf();
        return drawable;
    }

    public static Drawable setDrawableColor(Context context,int color,@DrawableRes int drawableRes)
    {
        Drawable drawable=ContextCompat.getDrawable(context,drawableRes);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        drawable.invalidateSelf();
        return drawable;
    }


    public static <T extends Drawable> T tintDrawable(ColorStateList colorStateList, Drawable drawable) {
        Drawable newDrawable = DrawableCompat.wrap(drawable);
        setBitmapDrawableTintColor(colorStateList, newDrawable);
        newDrawable = newDrawable.mutate();
        newDrawable.invalidateSelf();
        return (T) newDrawable;
    }

    public static void setBitmapDrawableTintColor(int color, Drawable... drawables) {
        setBitmapDrawableTintColor(ColorStateList.valueOf(color), drawables);
    }

    public static void setBitmapDrawableTintColor(ColorStateList colorStateList, Drawable... drawables) {
        for (Drawable item : drawables) {
            if (item != null) {
                DrawableCompat.setTintList(item, colorStateList);
            }
        }
    }

    public static LinearLayoutManager generateRecyclerLinearLayoutManager(Context context,boolean isVertical)
    {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        if(!isVertical)
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return layoutManager;
    }

    public static GridLayoutManager generateRecyclerGridLayoutManager(Context context, boolean isVertical,int spanCount)
    {
        GridLayoutManager layoutManager=new GridLayoutManager(context,spanCount);
        if(!isVertical)
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return layoutManager;
    }

    public static <T> T invokeFiled(Object object, String fieldName) {
        Object returnObj = null;
        if (object == null || TextUtils.isEmpty(fieldName)) {
            return null;
        }
        Class cls = object.getClass();
        Field field = null;
        for (; cls != Object.class; cls = cls.getSuperclass()) {
            try {
                field = cls.getDeclaredField(fieldName);
                break;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (field != null) {
            field.setAccessible(true);
            try {
                returnObj = field.get(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) returnObj;
    }

    public static void showToast(Context context,String msg)
    {
        Toast.makeText(searchTintContextHostActivity(context),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showToast(Context context,@StringRes int msg)
    {
        Toast.makeText(searchTintContextHostActivity(context),msg,Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view, String msg)
    {
        Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view, @StringRes int msg)
    {
        Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 取某个资源文件的id
     *
     * @param context
     * @param fileName 文件名
     * @param fileType 文件类型
     * @return
     */
    public static int getResId(Context context, String fileName,@AndroidResType String fileType) {
        AssetManager assetManager = context.getResources().getAssets();
        try {
            Method getResourceIdentifier = assetManager.getClass().getDeclaredMethod("getResourceIdentifier", String.class, String.class, String.class);
            getResourceIdentifier.setAccessible(true);
            return (Integer) getResourceIdentifier.invoke(assetManager, fileName, fileType, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getResources().getIdentifier(fileName, fileType, context.getPackageName());
    }


    public static FragmentActivity searchTintContextHostActivity(Context context)
    {
        Context hostContext=context;
        while(hostContext instanceof ContextWrapper)
        {
            if(hostContext instanceof FragmentActivity)
                return (FragmentActivity) hostContext;
            hostContext=((ContextWrapper)context).getBaseContext();
        }
        return  null;
    }

    public static int getStatusBarHeight(Resources res)
    {
        return getInternalDimensionSize(res,"status_bar_height");
    }

    public static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }

        return result;
    }

    public static boolean queryIntentActivities(Context context, Intent intent) {
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfoList != null && resolveInfoList.size() > 0;
    }

    public static String queryIntentClassName(Context context, Intent intent) {
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(resolveInfoList != null && resolveInfoList.size() > 0)
        {
            return resolveInfoList.get(0).activityInfo.name;
        }
        return null;
    }

    public static String  queryIntentActivityName(Context context, Intent intent) {
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(resolveInfoList != null && resolveInfoList.size() > 0)
        {
            return resolveInfoList.get(0).loadLabel(context.getPackageManager()).toString();
        }
        return null;
    }
}
