package online.ukapps.raspi_drone;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.cameraview.CameraView;

/**
 * Created by root on 15/3/18.
 */

public class CustomCameraView extends CameraView {

    Context mContext = null;

    public CustomCameraView(Context context){
        super(context);
        mContext = this.getContext();
    }

    public CustomCameraView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        mContext = context;
    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

}
