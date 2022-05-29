package dev.azsoft.wifiattendance.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import dev.azsoft.wifiattendance.R;

public class ExtendedButton extends RelativeLayout {
    private TypedArray attributes;
    private Context mContext;
    private MaterialButton extBtn;
    private RelativeLayout extRL;
    private CircularProgressIndicator extCircularProgressIndicator;
    private LayoutParams layoutParams;


    public ExtendedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int pixelsFromDP(int pixels) {
        return (int) (pixels * mContext.getResources().getDisplayMetrics().density + 0.5f);
    }


    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        inflate(context, R.layout.extended_button, this);

        attributes = context.obtainStyledAttributes(attrs, R.styleable.ExtendedButton);
        extBtn = findViewById(R.id.extended_btn);
        extRL = findViewById(R.id.extended_rl);
        extCircularProgressIndicator = findViewById(R.id.extended_circular_progress_indicator);
        extSetLayoutParams();
//        System.out.println("ExtendedButton.init: width " + attributes.getString(R.styleable.ExtendedButton_android_layout_height));
//        LayoutParams params = new LayoutParams(extRL.getLayoutParams().width, extRL.getLayoutParams().height);
//        System.out.println("ExtendedButton.init:" + extRL.getLayoutParams().width);


        CharSequence text = getText();
        int cornerRadius = getCornerRadius();
        boolean isProcessing = isProcessing();
        int indicatorSize = getIndicatorSize();
        int trackThickness = getTrackThickness();
        int backgroundTint = getBackgroundTint();
        int indicatorColor = getIndicatorColor();

        setText(text);
        setCornerRadius(cornerRadius);
        setProcessing(isProcessing);
        setIndicatorSize(indicatorSize);
        setTrackThickness(trackThickness);
        setBackgroundTint(backgroundTint);
    }

    public CharSequence getText() {
        CharSequence value = attributes.getText(R.styleable.ExtendedButton_extText);
        return value != null ? value : "Button";
    }

    public boolean isProcessing() {
        return attributes.getBoolean(R.styleable.ExtendedButton_isProcessing, false);
    }

    public int getCornerRadius() {
        return attributes.getInt(R.styleable.ExtendedButton_extCornerRadius, (int) getResources().getDimension(R.dimen.default_radius));
    }

    public int getIndicatorSize() {
        return attributes.getInt(R.styleable.ExtendedButton_extIndicatorSize, (int) getResources().getDimension(R.dimen.default_indicatorSize));
    }

    public int getTrackThickness() {
        return attributes.getInt(R.styleable.ExtendedButton_extIndicatorSize, (int) getResources().getDimension(R.dimen.default_trackThickness));
    }

    public int getBackgroundTint() {
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
        int color = ContextCompat.getColor(mContext, typedValue.resourceId);
        return attributes.getInt(R.styleable.ExtendedButton_extBackgroundTint, color);
    }

    public int getIndicatorColor() {
        return 0;
//        TypedValue typedValue = new TypedValue();
//        mContext.getTheme().resolveAttribute(androidx.appcompat.R.attr.coloro, typedValue, true);
//        int color = ContextCompat.getColor(mContext, typedValue.resourceId);
//        return attributes.getInt(R.styleable.ExtendedButton_extIndicatorColor, color);
    }


    public void setText(CharSequence text) {
        extBtn.setText(text);
    }

    public void setProcessing(boolean processing) {
        if (processing) {
            extCircularProgressIndicator.setVisibility(VISIBLE);
            extBtn.setText("");
            extBtn.setClickable(false);
        } else {
            extCircularProgressIndicator.setVisibility(GONE);
            extBtn.setText(getText());
            extBtn.setClickable(true);
        }
    }

    public void setCornerRadius(int cornerRadius) {
        extBtn.setCornerRadius(cornerRadius);
    }

    public void setIndicatorSize(int indicatorSize) {
        extCircularProgressIndicator.setIndicatorSize(indicatorSize);
    }

    public void setTrackThickness(int trackThickness) {
        extCircularProgressIndicator.setTrackThickness(trackThickness);

    }

    public void setBackgroundTint(int backgroundTint) {
//        extBtn.setBackgroundTintList(ContextCompat.getColorStateList(mContext, backgroundTint));
    }

    public void extSetLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        layoutParams.setMargins(0, 0, 0, 0);
        extRL.setLayoutParams(layoutParams);
    }

    private void extSetLayoutParams() {
        System.out.println("ExtendedButton.init: width " + attributes.getString(R.styleable.ExtendedButton_android_layout_width));
        System.out.println("ExtendedButton.init: height " + attributes.getString(R.styleable.ExtendedButton_android_layout_height));
        String height = attributes.getString(R.styleable.ExtendedButton_android_layout_height);
        String width = attributes.getString(R.styleable.ExtendedButton_android_layout_width);
        RelativeLayout.LayoutParams layoutParams;
//        LayoutParams.WRAP_CONTENT = -2
//        LayoutParams.MATCH_PARENT = -1
        if ((width.equals("-1") || width.equals("-2")) && height.equals("-2")) {
            layoutParams = new LayoutParams(Integer.parseInt(width), 168);
        } else if ((width.equals("-1") || width.equals("-2")) && height.equals("-1")) {
            layoutParams = new LayoutParams(Integer.parseInt(width), Integer.parseInt(height));
        } else {
            layoutParams = new LayoutParams(-2, -2);

        }

//        extSetLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, pixelsFromDP(56)));

//        int height = attributes.getDimensionPixelSize(R.styleable.ExtendedButton_android_layout_width, 1);
//        System.out.println("ExtendedButton.init: " + height);


        layoutParams.setMargins(0, 0, 0, 0);
        extRL.setLayoutParams(layoutParams);
    }

}
