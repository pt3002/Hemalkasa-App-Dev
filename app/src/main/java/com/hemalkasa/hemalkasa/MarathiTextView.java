package com.hemalkasa.hemalkasa;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarathiTextView extends androidx.appcompat.widget.AppCompatTextView {

    public MarathiTextView(Context context) {
        super(context);
        initTypeface(context);
    }

    public MarathiTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        initTypeface(context);
    }

    public MarathiTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initTypeface(context);
    }

    private void initTypeface(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "marathi.TTF");
        this.setTypeface(typeface);
    }
}
