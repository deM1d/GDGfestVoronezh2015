package idemidov.com.gdgvrn2015.activity;

import android.app.Activity;
import android.os.Bundle;
import idemidov.com.gdgvrn2015.R;
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
        GdgView view2 = (GdgView) findViewById(R.id.gdg_id2);
        GdgView view3 = (GdgView) findViewById(R.id.gdg_id3);

        textView.setText("HELLO");
        view1.setText("GDG");
        view2.setText("VRN");
        view3.setText("2015");
    }
}
