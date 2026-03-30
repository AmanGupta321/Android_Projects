package com.devaman.weather.javaFile;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class HindiTextView extends androidx.appcompat.widget.AppCompatTextView {
    public HindiTextView(Context context) {
        super(context);
        initTypeFace(context);
    }

    public HindiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeFace(context);
    }

    public HindiTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeFace(context);
    }
    private void initTypeFace(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "NotoSansDevanagariRegular.ttf");
        this.setTypeface(typeface);
    }
}
