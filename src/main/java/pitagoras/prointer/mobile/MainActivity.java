package pitagoras.prointer.mobile;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import pitagoras.prointer.mobile.view.ViewActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        CardView mButtonDownload = (CardView) findViewById(R.id.card_view_download);
        mButtonDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                downloadProgress();

                //
                startActivity(new Intent(getBaseContext(), ViewActivity.class));


            }
        });

        CardView mButtonEdit = (CardView) findViewById(R.id.card_view_edit);
        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProgress();
            }
        });

        CardView mButtonSave = (CardView) findViewById(R.id.card_view_save);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProgress();
            }
        });

        CardView mButtonHistory = (CardView) findViewById(R.id.card_view_history);
        mButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyProgress();
            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getBaseContext(), TestActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_download) {
            downloadProgress();
        } else if (id == R.id.nav_edit) {
            editProgress();
        } else if (id == R.id.nav_save) {
            saveProgress();
        } else if (id == R.id.nav_history) {
            historyProgress();
        } else if (id == R.id.nav_shared) {

        } else if (id == R.id.nav_contact) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void downloadProgress() {
        if (Util.getFile(getBaseContext()).exists()) alertFileExist();
        else startActivity(new Intent(getBaseContext(), DownloadProgressActivity.class));
    }

    private void saveProgress() {
        startActivity(new Intent(getBaseContext(), SaveProgressActivity.class));    }

    private void historyProgress() {
        startActivity(new Intent(getBaseContext(), HistoryActivity.class));
    }

    private void editProgress() {

        File file = Util.getFile(getBaseContext());
        if (!file.exists()){
            startActivity(new Intent(getBaseContext(), DownloadProgressActivity.class));
        }else {
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file), "application/msword");
            Intent intent = Intent.createChooser(target, getString(R.string.message_open_file));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getBaseContext(), getString(R.string.install_word), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void alertFileExist(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Descartar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertConfirm();
            }
        });
        builder.setNegativeButton("Abrir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editProgress();
            }
        });

        builder.setMessage("Já existe uma versão do arquivo salva no dispositivo.\nVoçê pode abrir o arquivo salvo ou descartar as informações não sincronizadas.");
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void alertConfirm() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(getBaseContext(), DownloadProgressActivity.class));
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    editProgress();
                }
            });

            builder.setMessage("Deseja apagar a verssão baixada?\nCaso apague, perderá qualquer alteração feita no arquivo que não foi sincronizada.");
            AlertDialog dialog = builder.create();

            dialog.show();


    }

}