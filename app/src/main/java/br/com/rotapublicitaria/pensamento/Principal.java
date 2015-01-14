package br.com.rotapublicitaria.pensamento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Principal extends Activity implements ConsultaPensamentos.ConsultaPensamentosListener {

    private TextView pensamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        pensamento = (TextView) findViewById(R.id.pensamento);

        AtualizaPensamento();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.atualizar:

                AtualizaPensamento();

                return true;

            case R.id.compartilhar:

                compartilhar();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void compartilhar() {

        TextView pensamento = (TextView) findViewById(R.id.pensamento);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, pensamento.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }

    @Override
    public void onConsultaConcluida(String s) {

        if(s != null) {
            pensamento.setText(s);
        } else {
            pensamento.setText("Sem conexão =/");
        }

    }

    public void AtualizaPensamento() {

        pensamento.setText("Atualizando...");

        new ConsultaPensamentos(this).execute();

    }

}
