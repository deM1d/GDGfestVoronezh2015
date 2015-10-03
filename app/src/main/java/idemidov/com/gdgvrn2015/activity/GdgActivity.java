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
        GdgView view1 = (GdgView) findViewById(R.id.gdg_id1);
        GdgView view2 = (GdgView) findViewById(R.id.gdg_id2);
        GdgView view3 = (GdgView) findViewById(R.id.gdg_id3);
        GdgableTextView textView = (GdgableTextView) findViewById(R.id.gdg_id4);
        GdgView view5 = (GdgView) findViewById(R.id.gdg_id5);

        view1.setText("1010101001");
        view2.setText("0101011100");
        view3.setText("0101010101");
        textView.setText("0101011100");
        view5.setText("1010100100");
    }
}
