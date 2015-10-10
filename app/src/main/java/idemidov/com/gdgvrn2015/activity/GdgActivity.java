package idemidov.com.gdgvrn2015.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import idemidov.com.gdgvrn2015.R;
import idemidov.com.gdgvrn2015.widget.GdgLayout;
import idemidov.com.gdgvrn2015.widget.GdgView;
import idemidov.com.gdgvrn2015.widget.GdgableTextView;

/**
 * Created by Ilya on 03/10/2015.
 */
public class GdgActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdg);

        initView();
    }

    private void initView() {
        GdgableTextView textView = (GdgableTextView) findViewById(R.id.gdg_tv);
        GdgView view1 = (GdgView) findViewById(R.id.gdg_id1);

        textView.setText("GDG");
        view1.setText("VRN");

        GdgLayout l1 = (GdgLayout) findViewById(R.id.gdg_group1);
        l1.setBackgroundColor(Color.rgb(0x42, 0xaa, 0xff));
        GdgLayout l2 = (GdgLayout) findViewById(R.id.gdg_group2);
        l2.setBackgroundColor(Color.rgb(0xff, 0x80, 0xff));
    }
}
