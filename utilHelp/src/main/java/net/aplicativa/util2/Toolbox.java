package net.aplicativa.util2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.google.android.youtube.player.YouTubeBaseActivity;

/**
 * Created by Luis Castillo
 */
public class Toolbox {

    /**
     * Colorear el borde de un botón
     *
     * @param b           botón
     * @param bgColor     color del fondo para el botón
     * @param borderColor color del borde para el botón
     * @param radio       ancho de la linea del borde del botón
     */
    public void bordecolorboton(Button b, int bgColor, int borderColor, int radio) {
        if (b != null) {
            GradientDrawable gd = new GradientDrawable();
            if (bgColor != 0) {
                gd.setColor(bgColor);
            }
            gd.setCornerRadius(radio);
            gd.setStroke(1, borderColor);
            b.setBackground(gd);
        }
    }

    /**
     * Asignar tipo de fuente a un label
     *
     * @param context   context donde se encuentra el componente
     * @param directory ruta donde se encuentra el archivo de la fuente
     * @param txt       componete al que se desea cambiar la fuente
     */
    public void configureFonts(Context context, String directory, TextView txt) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), directory);
        txt.setTypeface(font);
    }

    /**
     * Asignar tipo de fuente a un label con una actividad de tipo YouTubeBaseActivity
     *
     * @param context   context donde se encuentra el componente
     * @param directory ruta donde se encuentra el archivo de la fuente
     * @param txt componete al que se desea cambiar la fuente
     */
//    public   void configureFontsYoutube(YouTubeBaseActivity activity, String directory, TextView txt) {
//        Typeface font = Typeface.createFromAsset(activity.getAssets(), directory);
//        txt.setTypeface(font);
//    }

    /**
     * Asignar tipo de fuente a un botón
     *
     * @param context   actividad donde se encuentra el componente
     * @param directory ruta donde se encuentra el archivo de la fuente
     * @param btn       componete al que se desea cambiar la fuente
     */
    public void configureFontsButton(Context context, String directory, Button btn) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), directory);
        btn.setTypeface(font);
    }

    /**
     * Asignar tipo de fuente a un botón con una actividad de tipo YouTubeBaseActivity
     *
     * @param activity actividad donde se encuentra el componente
     * @param directory ruta donde se encuentra el archivo de la fuente
     * @param btn componete al que se desea cambiar la fuente
     */
//    public   void configureFontsButtonYoutube(YouTubeBaseActivity activity, String directory, Button btn) {
//        Typeface font = Typeface.createFromAsset(activity.getAssets(), directory);
//        btn.setTypeface(font);
//    }

    /**
     * Realizar una llamada a un número teléfonico
     *
     * @param context actividad donde se encuentra el componente
     * @param phone   número de teléfono que desea realizar la llamada
     */
    public void callPhone(Context context, String phone) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + phone));

            Intent chooserIntent = Intent.createChooser(intent, "Open With");
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(chooserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Enviar correo electrónico
     *
     * @param context actividad donde se encuentra el componente
     * @param email   correo electrónico al que desea enviar el correo
     */
    public void sendEmail(Context context, String email) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abrir una determinada ubicación con todas las posibles aplicaciones de geolocalización
     * que se encuentran en el dispositivo
     *
     * @param context   actividad donde se encuentra el componente
     * @param latitude  latitud del lugar a encontrar
     * @param longitude longitud del lugar a encontrar
     */
    public void openGeolocalitation(Context context, String latitude, String longitude) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitude + "," + longitude)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abrir una determinada ubicación con la aplicación WAZE
     *
     * @param context   actividad donde se encuentra el componente
     * @param latitude  latitud del lugar a encontrar
     * @param longitude longitud del lugar a encontrar
     */
    public boolean openGeolocalitationWaze(Context context, double latitude, double longitude) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("waze://?ll=" + latitude + "," + longitude + "&navigate=yes")));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Abrir un perfil o página de Facebook, ya sea con la aplicación o desde el navegador
     * del dispositivo
     *
     * @param context context donde se encuentra el componente
     * @param url     URL del perfil o página de Facebook
     * @param id      id del perfil o página de Facebook
     */
    public void openFacebook(Context context, String url, String id) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + id)));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    /**
     * Abrir un chat de Messenger de un perfil o página de Facebook
     *
     * @param context actividad donde se encuentra el componente
     * @param id      id del perfil o página de Facebook
     */
    public void openMessenger(Context context, String id) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/" + id)));
        } catch (Exception e) {
        }
    }

    /**
     * Abrir un perfil o página de Twitter, ya sea con la aplicación o desde el navegador
     * del dispositivo
     *
     * @param context actividad donde se encuentra el componente
     * @param url     URL del perfil o página de Twitter
     * @param id      id del perfil o página de Twitter
     */
    public void openTwiter(Context context, String url, String id) {

        Intent intent = null;
        try {
            // get the Twitter app if possible
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=" + id));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        }
        context.startActivity(intent);
    }

    /**
     * Abrir un perfil o página de Instagram, ya sea con la aplicación o desde el navegador
     * del dispositivo
     *
     * @param context actividad donde se encuentra el componente
     * @param user    usuario del perfil o página de Instagram
     */
    public void openInstagram(Context context, String user) {

        String scheme = "http://instagram.com/_u/" + user;
        String path = "https://instagram.com/" + user;
        String nomPackageInfo = "com.instagram.android";
        try {
            context.getPackageManager().getPackageInfo(nomPackageInfo, 0);
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(scheme)));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(path)));
        }
    }

    /**
     * Abrir una página web desde el navegador del dispositivo
     *
     * @param context actividad donde se encuentra el componente
     * @param url     URL de la página web
     */
    public void openPageBrowser(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * Verificar si el dispositivo tiene acceso a internet
     * True si hay
     * False no hay
     */
    public boolean internet() {
        try {
            Process p = Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Descargar un archivo pdf
     *
     * @param fileUrl   url del archivo
     * @param directory directorio en el que se desea guardar el archivo
     */
    public void downloadFile(String fileUrl, File directory) {
        try {

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Limpiar la pila de fragmentos que existe en ejecución
     *
     * @param activity actividad donde se encuentra el componente
     */
    public void clearAddToBackStack(AppCompatActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    /**
     * Convertir una cadena en letras mayusculas y minusculas
     * <p>
     * Ejemplo:
     * <p>
     * cadena = ANDROID
     * <p>
     * Resultado que devuelve el método = Android
     *
     * @param cadena palabra que desea cambiar a mayúsculas y minúsculas
     */
    public String ucFirst(String cadena) {
        char[] caracteres = cadena.toCharArray();

        for (int i = 0; i < cadena.length() - 1; i++)
            // Es 'palabra'
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',')
                // Reemplazamos
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
            else
                caracteres[i + 1] = Character.toLowerCase(caracteres[i + 1]);

        String x = new String(caracteres);
        x.toString();
        return new String(caracteres);
    }

    /**
     * This method show a custom alert
     *
     * @param activity context
     * @param title title of the alert
     * @param content Content of the alert
     * @param txtButton Text of the button
     * @param cancelable True = Dissmissable with a touch, False = No Dissmissable with a Touch
     */
    public AlertDialog dialog;
    public AlertDialog showAlertDialog(Context context,
                                String title,
                                String content,
                                String txtButton,
                                String color1,
                                String color2,
                                boolean cancelable) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.generic_dialog, null);
        mBuilder.setView(mView);

        TextView txtTitle = mView.findViewById(R.id.txtTitle);
        txtTitle.setBackgroundColor(Color.parseColor(color1));
        txtTitle.setText(title);

        TextView txtContent = mView.findViewById(R.id.txtContent);
        txtContent.setBackgroundColor(Color.parseColor(color2));
        txtContent.setText(content);

        Button btnCancel = mView.findViewById(R.id.btnCancel);
        btnCancel.setText(txtButton);
        btnCancel.setBackgroundColor(Color.parseColor(color1));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog = mBuilder.create();
        if (!cancelable) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        return dialog;
    }

    public void cancelShowAlertDialog(){
        dialog.dismiss();
    }

    public AlertDialog dialogLoading;
    public AlertDialog loadingDialog(Context context, String msj) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.dialog_loading_confirm, null);
        mBuilder.setView(mView);

        TextView txtInfo = (TextView) mView.findViewById(R.id.txtInfo);
        txtInfo.setText(msj);

        dialogLoading = mBuilder.create();
        dialogLoading.setCancelable(false);
        dialogLoading.setCanceledOnTouchOutside(false);
        return dialogLoading;
    }

    public void cancelLoadingDialog(){
        dialog.dismiss();
    }

    /**
     * Variable que verifica el formato del correo
     */
    public final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validate given email with regular expression.
     *
     * @param email email for validation
     * @return true valid email, otherwise false
     */
    public boolean validateEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public String encodeURL(String base_url, String txt) {
        String nameEncode = "";
        try {
            nameEncode = URLEncoder.encode(txt, "UTF-8");//changed
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String URL = base_url + nameEncode;
        URL = URL.replace("+", "%20");
        return URL;
    }

    public String convertAmount(String amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("₡ #,###", symbols);
        double parsed = Double.parseDouble(amount);
        String formatted = decimalFormat.format(parsed);
        return formatted;
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public String clearAscii(String query) {
        String clean = null;
        if (query != null) {
            String value = query;
            value = value.toUpperCase();
            // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
            clean = Normalizer.normalize(value, Normalizer.Form.NFD);
            // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
            clean = clean.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
            // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
            clean = Normalizer.normalize(clean, Normalizer.Form.NFC);
        }
        return clean;
    }
}
