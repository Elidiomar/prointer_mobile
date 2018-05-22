package pitagoras.prointer.mobile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pitagoras.prointer.mobile.R;
import pitagoras.prointer.mobile.web.service.AlunoService;
import pitagoras.prointer.mobile.web.service.UsuarioService;

public class ViewActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        textView  = findViewById(R.id.txt_view_result);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //new AlunoService().getAlunos(textView, this);
        new UsuarioService().getUserByEmail(textView, this);
    }
}
