package es.studium.practicatema2pmdm;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
, View.OnClickListener{

    Spinner spinner;
    String[] estadoCivil;
    Button buttonAceptar;
    Button buttonLimpiar;

    EditText editTextNombre, editTextApellidos, editTextEdad ;

    Switch switch1;

    RadioGroup radioGroup;

    TextView textVoid;
     Button buttonIdioma;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = findViewById(R.id.spinnerCivil);
        estadoCivil = new String[]{
                getString(R.string.spinnerSeleccione),
                getString(R.string.spinnerSoltero),
                getString(R.string.spinnerCasado),
                getString(R.string.spinnerSeparado),
                getString(R.string.spinnerViudo),
                getString(R.string.spinnerOtro)
        };
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estadoCivil);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptador);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextEdad = findViewById(R.id.editTextEdad);


    buttonLimpiar = findViewById(R.id.buttonLimpiar);
    buttonLimpiar.setOnClickListener(this);

    buttonAceptar = findViewById(R.id.buttonAceptar);
    buttonAceptar.setOnClickListener(this);

        radioGroup = findViewById(R.id.radioGroup);
        switch1 = findViewById(R.id.switchHijos);
        textVoid =  findViewById(R.id.textVoid);

        buttonIdioma = findViewById(R.id.buttonIdioma);

        buttonIdioma.setOnClickListener(v -> {
            String currentLang = java.util.Locale.getDefault().getLanguage();

            // Cambiar cíclicamente entre español - inglés - italiano - español
            switch (currentLang) {
                case "es":
                    setLocale("en");
                    break;
                case "en":
                    setLocale("it");
                    break;
                default:
                    setLocale("es");
                    break;
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonLimpiar)
        {
            editTextNombre.setText("");
            editTextApellidos.setText("");
            editTextEdad.setText("");

            radioGroup.clearCheck();

            spinner.setSelection(0);

            switch1.setChecked(false);

            editTextNombre.requestFocus();
        }
        if(v.getId() == R.id.buttonAceptar)
        {
           if (editTextNombre.getText().toString().isBlank())
           {
               textVoid.setText(getString(R.string.textFaltaNombre));
           }
            else if (editTextApellidos.getText().toString().isBlank())
            {
                textVoid.setText(getString(R.string.textFaltaApellidos));
            }
           else if (editTextEdad.getText().toString().isBlank())
           {
               textVoid.setText(getString(R.string.textFaltaEdad));
           }
           else if (radioGroup.getCheckedRadioButtonId() == -1)
           {
               textVoid.setText(getString(R.string.textFaltaGenero));
           }
           else if (spinner.getSelectedItemPosition() == 0)
           {
               textVoid.setText(getString(R.string.textFaltaEstado));
           }
           else
           {
               String tieneHijos = "";
               int idBotonSeleccionado = radioGroup.getCheckedRadioButtonId();
               String genero = "";

               if (switch1.isChecked())
               {
                   tieneHijos = getString(R.string.siHijos);
               }
               else
               {
                   tieneHijos = getString(R.string.noHijos);
               }

               if (idBotonSeleccionado == R.id.radioButtonHombre)
               {
                   genero = getString(R.string.opcionHombre);
               }
               else if (idBotonSeleccionado == R.id.radioButtonMujer)
               {
                   genero = getString(R.string.opcionMujer);
               }

               String saberEdad = editTextEdad.getText().toString();
               int edad = Integer.parseInt(saberEdad);

               if (edad >=18)
               {
                   Toast.makeText(MainActivity.this, editTextNombre.getText() + ", " + editTextApellidos.getText() + ", "+ getString(R.string.mayorEdad) + ", "+ tieneHijos + ", " + genero + ", " + spinner.getSelectedItem().toString() + "." , Toast.LENGTH_LONG).show();
                   textVoid.setText("");
               }
               else
               {
                   Toast.makeText(MainActivity.this, editTextNombre.getText() + ", " + editTextApellidos.getText() + ", "+ getString(R.string.menorEdad) + ", "+ tieneHijos + ", " + genero + ", " + spinner.getSelectedItem().toString() + "." , Toast.LENGTH_LONG).show();
                   textVoid.setText("");
               }
           }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void setLocale(String langCode) {
        java.util.Locale locale = new java.util.Locale(langCode);
        java.util.Locale.setDefault(locale);

        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        recreate(); // Reinicia la actividad para aplicar el cambio
}
}