package project.sideproject.com.zumperinterview;

import android.graphics.Typeface;
import android.view.View;

/**
 * Created by Shishir on 2/4/2017.
 */
public class Fonts {

    public static Typeface getRobotoBlack(View view){
        Typeface typeface = Typeface.createFromAsset(view.getResources().getAssets(),"fonts/Roboto-Black.ttf");
        return typeface;
    }

    public static Typeface getRobotoLight(View view){
        Typeface typeface = Typeface.createFromAsset(view.getResources().getAssets(), "fonts/Roboto-Light.ttf");
        return typeface;
    }

    public static Typeface getRobotoThin(View view){
        Typeface typeface = Typeface.createFromAsset(view.getResources().getAssets(),"fonts/Roboto-Thin.ttf");
        return typeface;
    }

    public static Typeface getRobotoCondensedLight(View view){
        Typeface typeface = Typeface.createFromAsset(view.getResources().getAssets(),"fonts/RobotoCondensed-Light.ttf");
        return typeface;
    }

    public static Typeface getRobotoRegular(View view){
        Typeface typeface = Typeface.createFromAsset(view.getResources().getAssets(),"fonts/Roboto-Regular.ttf");
        return typeface;
    }
}
