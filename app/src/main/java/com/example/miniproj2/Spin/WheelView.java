package com.example.miniproj2.Spin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.miniproj2.R;

import java.net.ContentHandler;
import java.util.List;

public class WheelView extends RelativeLayout implements pieView.PieRotateListener {
    private int mBackgroundColor,mTextColor;

    private Drawable mCenterImage,mCursorImage;
    private pieView pieView;
    private ImageView imgCursor;


    private LuckyRoundItemSelectedListener itemSelectedListener;

    public void LuckRoundItemSelectedListener(LuckyRoundItemSelectedListener listener)
    {
        this.itemSelectedListener=listener;
    }

    public interface LuckyRoundItemSelectedListener
    {
        void LuckyRoundItemSelected(int index);
    }


    @Override
    public void rotateDone(int index) {
            if(itemSelectedListener!=null)
            {
                itemSelectedListener.LuckyRoundItemSelected(index);

            }
    }

    public WheelView (Context context)
    {
        super(context);
        inits(context,null);

    }
    public WheelView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        inits(context,attributeSet);
    }

    private void inits(Context context,AttributeSet attrs)
    {
        if(attrs !=null)
        {

            TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.WheelView);

            mBackgroundColor =typedArray.getColor(R.styleable.WheelView_BackgroundColor,0xffcc0000);
            mTextColor=typedArray.getColor(R.styleable.WheelView_TextColor,0xffffffff);

            mCenterImage=typedArray.getDrawable(R.styleable.WheelView_CursorImage);
            mCursorImage=typedArray.getDrawable(R.styleable.WheelView_CenterImage);

            typedArray.recycle();

        }
        LayoutInflater inflater=LayoutInflater.from(getContext());

        FrameLayout frameLayout=(FrameLayout) inflater.inflate(R.layout.wheel_layout,this,false);

        pieView=(pieView) frameLayout.findViewById(R.id.pieView);
        imgCursor=frameLayout.findViewById(R.id.cursor_View);

        pieView.setPieRotateListener(this);
        pieView.setPieBackgroundColor(mBackgroundColor);
        pieView.setPieCenterImage(mCenterImage);
        pieView.setPieTextColor(mTextColor);

        imgCursor.setImageDrawable(mCursorImage);

        addView(frameLayout);

    }

    public void setWheelBackgroundColor(int color)
    {
        pieView.setPieBackgroundColor(color);
    }

    public void setWheelCursorImage(int drawable)
    {
        imgCursor.setBackgroundResource(drawable);

    }
    public void setWheelCenterImage(Drawable drawable)
    {
        pieView.setPieCenterImage(drawable);
    }

    public void setWheelTextColor(int color)
    {
        pieView.setPieTextColor(color);
    }

    public void setData(List<SpinItem> data)
    {
        pieView.setData(data);
    }
    public void setRound(int numberOfRound)
    {
        pieView.setRound(numberOfRound);
    }

    public void startWheelWIthTargetIndex(int index)
    {
        pieView.rotateTo(index);

    }
}
