package com.wangjie.imageloadersample.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * 设置bitmap时会显示淡入淡出的动画的ImageView
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-27
 * Time: 下午5:43
 */
public class FadeImageView extends ImageView{
    private AlphaAnimation alphaOut;
    private AlphaAnimation alphaIn;

    private Context context;
    public FadeImageView(Context context) {
        super(context);
        this.context = context;
    }

    public FadeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public FadeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    private AlphaAnimation getAlphaOut(){
        if(null != alphaOut){
            return alphaOut;
        }
        alphaOut = new AlphaAnimation(1.0f, 0f);
        alphaOut.setFillAfter(true);
        alphaOut.setDuration(200);
        return alphaOut;
    }

    private AlphaAnimation getAlphaIn(){
        if(null != alphaIn){
            return alphaIn;
        }
        alphaIn = new AlphaAnimation(0f, 1.0f);
        alphaIn.setFillAfter(true);
        alphaIn.setDuration(200);
        return alphaIn;
    }

    public void setImageBitmapAnim(final Bitmap bm) {
//        super.setImageBitmap(bm);
        getAlphaOut().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setImageDrawable(new BitmapDrawable(context.getResources(), bm));
                startAnimation(getAlphaIn());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.startAnimation(getAlphaOut());

    }





}
