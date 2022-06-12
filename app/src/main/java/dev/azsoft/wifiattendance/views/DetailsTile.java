package dev.azsoft.wifiattendance.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.imageview.ShapeableImageView;

import dev.azsoft.wifiattendance.R;

public class DetailsTile extends MaterialCardView {
    private TypedArray attributes;
    private Context mContext;
    private FrameLayout frameLayout;
    private ShapeableImageView shapeableImageView;
    private TextView textView;
    private MaterialCardView detailTileCard;
    private static final int DEFAULT_VALUE = 144;
    private LinearLayout leadingLL;
    private RelativeLayout leadingRL;

    public DetailsTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        inflate(context, R.layout.details_tile, this);
        attributes = context.obtainStyledAttributes(attrs, R.styleable.DetailsTile);
        textView = findViewById(R.id.text_view);
        shapeableImageView = findViewById(R.id.shapeable_image_view);
        detailTileCard = findViewById(R.id.details_tile_card);
        frameLayout = findViewById(R.id.frame_layout);
        leadingLL = findViewById(R.id.leading_ll);
        leadingRL = findViewById(R.id.leading_rl);

        CharSequence text = getText();
        setText(text);

        int textColor = getTextColor();
        setTextColor(textColor);

        Drawable drawable = getDrawable();
        setImageDrawable(drawable);

        int leadingTine = getLeadingTint();
        setLeadingTint(leadingTine);

        int leadingBackground = getLeadingBackground();
        setLeadingBackground(leadingBackground);

        int tileBackground = getTileBackground();
        setTileBackground(tileBackground);

        extSetLayoutParams();


    }


    private void extSetLayoutParams() {
        System.out.println("ExtendedButton.init: width " + attributes.getString(R.styleable.DetailsTile_android_layout_width));
        System.out.println("ExtendedButton.init: height " + attributes.getString(R.styleable.DetailsTile_android_layout_height));
        String height = attributes.getString(R.styleable.DetailsTile_android_layout_height);
        String width = attributes.getString(R.styleable.DetailsTile_android_layout_width);
        MaterialCardView.LayoutParams layoutParams;
//        LayoutParams.WRAP_CONTENT = -2
//        LayoutParams.MATCH_PARENT = -1


        if ((width.equals("-1") || width.equals("-2")) && (!height.equals("-2") && !height.equals("-1"))) {
            int pixelHeight = attributes.getDimensionPixelSize(R.styleable.DetailsTile_android_layout_height, MaterialCardView.LayoutParams.MATCH_PARENT);
            layoutParams = new MaterialCardView.LayoutParams(Integer.parseInt(width), pixelHeight);
            detailTileCard.setLayoutParams(layoutParams);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(pixelHeight, pixelHeight));
        } else if ((!width.equals("-1") && !width.equals("-2")) && (height.equals("-2") || height.equals("-1"))) {
            int matchWrapValue = height.equals("-2") ? DEFAULT_VALUE : Integer.parseInt(height);
            int pixelWidth = attributes.getDimensionPixelSize(R.styleable.DetailsTile_android_layout_width, MaterialCardView.LayoutParams.MATCH_PARENT);
            layoutParams = new MaterialCardView.LayoutParams(pixelWidth, matchWrapValue);
            detailTileCard.setLayoutParams(layoutParams);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(DEFAULT_VALUE, matchWrapValue));
        } else if ((width.equals("-1") || width.equals("-2")) && (height.equals("-2") || height.equals("-1"))) {
            int matchWrapValue = height.equals("-2") ? DEFAULT_VALUE : Integer.parseInt(height);
            layoutParams = new MaterialCardView.LayoutParams(Integer.parseInt(width), matchWrapValue);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(matchWrapValue, matchWrapValue));
        } else {
            int pixelHeight = attributes.getDimensionPixelSize(R.styleable.DetailsTile_android_layout_height, MaterialCardView.LayoutParams.MATCH_PARENT);
            int pixelWidth = attributes.getDimensionPixelSize(R.styleable.DetailsTile_android_layout_width, MaterialCardView.LayoutParams.MATCH_PARENT);
            layoutParams = new MaterialCardView.LayoutParams(pixelWidth, pixelHeight);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(pixelHeight, pixelHeight));
        }
        detailTileCard.setLayoutParams(layoutParams);
    }


    public CharSequence getText() {
        CharSequence value = attributes.getText(R.styleable.DetailsTile_android_text);
        return value != null ? value : getResources().getString(R.string.joseph_kamal);
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public int getTextColor() {
        return attributes.getColor(R.styleable.DetailsTile_android_textColor, MaterialColors.getColor(mContext, com.google.android.material.R.attr.title, Color.BLACK));
    }

    public void setTextColor(int textColor) {
        textView.setTextColor(textColor);
    }

    public Drawable getDrawable() {
        return attributes.getDrawable(R.styleable.DetailsTile_android_src);
    }

    public void setImageDrawable(Drawable drawable) {
        shapeableImageView.setImageDrawable(drawable);
    }


    public void setLeadingTint(int iconTint) {
        shapeableImageView.setImageTintList(ColorStateList.valueOf(iconTint));
    }

    public int getLeadingTint() {
        return attributes.getInt(R.styleable.DetailsTile_leadingTint, MaterialColors.getColor(mContext, com.google.android.material.R.attr.colorPrimary, Color.GRAY));
    }

    public int getLeadingBackground() {
        return attributes.getInt(R.styleable.DetailsTile_leadingBackground, MaterialColors.getColor(mContext, com.google.android.material.R.attr.colorPrimary, com.google.android.material.R.attr.colorPrimary));
    }

    public void setLeadingBackground(int color) {
        leadingLL.setBackgroundColor(color);
        leadingRL.setBackgroundColor(color);
    }

    public int getTileBackground() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = mContext.getTheme();
        theme.resolveAttribute(com.google.android.material.R.attr.cardBackgroundColor, typedValue, true);
        @ColorInt int color = typedValue.data;
        return attributes.getInt(R.styleable.DetailsTile_tileBackground, color);
    }

    public void setTileBackground(int color) {
        detailTileCard.setCardBackgroundColor(color);
    }


}
