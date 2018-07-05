package net.aplicativa.toolbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Luis Castillo
 */
public class Toolbox {


//    OPEN APPS

    /**
     * Open localitation with all apps
     *
     * @param context   activity context
     * @param latitude  latitude
     * @param longitude longitude
     */
    public static void openGeolocalitation(Context context, String latitude, String longitude) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("geo:" + latitude + "," + longitude));

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open localitation with WAZE
     *
     * @param context   activity context
     * @param latitude  latitude
     * @param longitude longitude
     */
    public static void openGeolocalitationWaze(Context context, String latitude, String longitude) {

        if (existApp(context, "com.waze")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("waze://?ll=" + latitude + "," + longitude + "&navigate=yes"));

                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            openAppPlayStore(context, "com.waze");
        }
    }

    /**
     * Open profile or page in facebook
     *
     * @param context activity context
     * @param id      id of profile or page
     * @param profile true = profile, false = page
     */
    public static void openFacebook(Context context, String id, boolean profile) {
        openFacebook(context, id, profile, false);
    }

    /**
     * Open profile or page in facebook
     *
     * @param context       activity context
     * @param id            id of profile or page
     * @param profile       true = profile, false = page
     * @param openInBrowser true = open in the browser, false = open in the play store
     */
    public static void openFacebook(Context context, String id, boolean profile, boolean openInBrowser) {
        if (existApp(context, "com.facebook.katana")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (profile) {
                    intent.setData(Uri.parse("fb://profile/" + id));
                } else {
                    intent.setData(Uri.parse("fb://page/" + id));
                }
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (openInBrowser) {
                openPageBrowser(context, "https://www.facebook.com/profile.php?id=" + id);
            } else {
                openAppPlayStore(context, "com.facebook.katana");
            }
        }
    }

    /**
     * Open chat in Messenger
     *
     * @param context activity context
     * @param id      id of profile or page
     */
    public static void openMessenger(Context context, String id) {

        if (existApp(context, "com.facebook.orca")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("fb://messaging/" + id));

                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            openAppPlayStore(context, "com.facebook.orca");
        }
    }

    /**
     * Open profile or page in Twitter
     *
     * @param context activity context
     * @param id      id of profile or page
     */
    public static void openTwitter(Context context, String id) {
        openTwiter(context, id, false);
    }

    /**
     * Open profile or page in Twitter
     *
     * @param context       activity context
     * @param id            id of profile or page
     * @param openInBrowser true = open in the browser, false = open in the play store
     */
    public static void openTwiter(Context context, String id, boolean openInBrowser) {

        if (existApp(context, "com.twitter.android")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("twitter://user?user_id=" + id));

                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (openInBrowser) {
                openPageBrowser(context, "https://twitter.com/" + id);
            } else {
                openAppPlayStore(context, "com.twitter.android");
            }
        }
    }

    /**
     * Open profile or page in Instagram
     *
     * @param context activity context
     * @param id      id of profile or page
     */
    public static void openInstagram(Context context, String id) {
        openInstagram(context, id, false);
    }

    /**
     * Open profile or page in Instagram
     *
     * @param context       activity context
     * @param id            id of profile or page
     * @param openInBrowser true = open in the browser, false = open in the play store
     */
    public static void openInstagram(Context context, String id, boolean openInBrowser) {
        if (existApp(context, "com.instagram.android")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("http://instagram.com/_u/" + id));

                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (openInBrowser) {
                openPageBrowser(context, "https://www.instagram.com/" + id);
            } else {
                openAppPlayStore(context, "com.instagram.android");
            }
        }
    }

    /**
     * Open any page in the Browser
     *
     * @param context activity context
     * @param url     Page's URL
     */
    public static void openPageBrowser(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check that package exist
     *
     * @param context     activity context
     * @param packageName Page's URL
     */
    public static boolean existApp(Context context, String packageName) {

        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Open app in the Play Store
     *
     * @param context     activity context
     * @param packageName Page's URL
     */
    public static void openAppPlayStore(Context context, String packageName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Make call
     *
     * @param context activity context
     * @param phone   number to call
     */
    public static void callPhone(Context context, String phone) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + phone));

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Send email
     *
     * @param context activity context
     * @param email   email
     */
    public static void sendEmail(Context context, String email) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("mailto:" + email));

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//   CONFIGURE

    /**
     * Paint the edge of a button
     *
     * @param b           button
     * @param bgColor     color for the background of button
     * @param borderColor color for the edge of button
     * @param oval        width for create the oval button
     * @param widthEdge   width for the line edge of button
     */
    public static void setEdgeButton(Button b,
                                     String bgColor,
                                     String borderColor,
                                     int oval,
                                     int widthEdge) {

        try {
            if (b != null) {
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.parseColor(bgColor));
                gd.setCornerRadius(oval);
                gd.setStroke(widthEdge, Color.parseColor(borderColor));
                b.setBackground(gd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set font to TextView
     *
     * @param context   activity context
     * @param directory Path of font
     * @param txt       Component TextView
     */
    public static void setFontsTextView(Context context, String directory, TextView txt) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), directory);
        txt.setTypeface(font);
    }

    /**
     * Set font to Button Text
     *
     * @param context   activity context
     * @param directory Path of font
     * @param btn       Component Button
     */
    public static void setFontsButton(Context context, String directory, Button btn) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), directory);
        btn.setTypeface(font);
    }

    /**
     * Clean string of special characters
     *
     * @param query your text
     */
    public static String clearAscii(String query) {
        String clean = null;
        if (query != null) {
            String value = query;
            value = value.toUpperCase();
            clean = Normalizer.normalize(value, Normalizer.Form.NFD);
            clean = clean.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
            clean = Normalizer.normalize(clean, Normalizer.Form.NFC);
        }
        return clean;
    }

    /**
     * @param s your text
     */
    public static String capitalize(String s) {
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


    /**
     * Encode the url so that it is compatible with the browser
     *
     * @param base_url   URL base
     * @param complement complement the URL
     */
    public static String encodeURL(String base_url, String complement) {
        String nameEncode = "";
        try {
            nameEncode = URLEncoder.encode(complement, "UTF-8");//changed
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String URL = base_url + nameEncode;
        URL = URL.replace("+", "%20");
        return URL;
    }


    /**
     * Format at amount in any currency
     * <p>
     * For example: You have a string="1000"
     * You call the method convertAmount and set the parameters ("1000", ' ', '.', "$ #,###")
     * So the method return "$ 1 000
     *
     * @param amount            Amount at parse
     * @param groupingSeparator Separator of numbers
     * @param decimalSeparator  Separator decimal
     * @param pattern           Conversion format
     */
    public static String convertAmount(String amount,
                                       char groupingSeparator,
                                       char decimalSeparator,
                                       String pattern) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(groupingSeparator);
        symbols.setDecimalSeparator(decimalSeparator);

        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        double parsed = Double.parseDouble(amount);
        String formatted = decimalFormat.format(parsed);
        return formatted;
    }


//  VALIDATE

    /**
     * Check the connexion
     * Return true = Is connection
     * Return false = Isn't connection
     */
    public static boolean isConnected() {
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
     * Check given email with regular expression.
     *
     * @param email email for validation
     * @return true valid email, otherwise false
     */
    public static boolean validateEmail(String email) {

        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }


//    DIALOGS


    public static AlertDialog dialog;

    /**
     * This method show a custom alert
     *
     * @param activity   activity context
     * @param title      title of the alert
     * @param content    Content of the alert
     * @param txtButton  Text of the button
     * @param color1     Color primary
     * @param color2     Color secundary
     * @param cancelable True = Dissmissable with a touch, False = No Dissmissable with a Touch
     */
    public static void showAlertDialog(Activity activity,
                                       String title,
                                       String content,
                                       String txtButton,
                                       String color1,
                                       String color2,
                                       boolean cancelable) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(net.aplicativa.toolbox.R.layout.generic_dialog, null);
        mBuilder.setView(mView);

        TextView txtTitle = mView.findViewById(net.aplicativa.toolbox.R.id.txtTitle);
        txtTitle.setTextColor(Color.parseColor(color1));
        txtTitle.setText(title);

        TextView txtContent = mView.findViewById(net.aplicativa.toolbox.R.id.txtContent);
        txtContent.setTextColor(Color.parseColor(color2));
        txtContent.setText(content);

        Button btnCancel = mView.findViewById(net.aplicativa.toolbox.R.id.btnCancel);
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
        dialog.show();
    }

    public static void cancelShowAlertDialog() {
        dialog.dismiss();
    }

    public static AlertDialog dialogLoading;

    /**
     * This method show a custom alert
     *
     * @param activity         activity context
     * @param msj              Content the message
     * @param colorProgressBar Color progressBar
     * @param colorText        Color TextView
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void loadingDialog(Activity activity, String msj, String colorProgressBar, String colorText) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(net.aplicativa.toolbox.R.layout.dialog_loading_confirm, null);
        mBuilder.setView(mView);

        ProgressBar progressBar = mView.findViewById(net.aplicativa.toolbox.R.id.progressBar);
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.parseColor(colorProgressBar)));

        TextView txtInfo = (TextView) mView.findViewById(net.aplicativa.toolbox.R.id.txtInfo);
        txtInfo.setText(msj);
        txtInfo.setTextColor(Color.parseColor(colorText));

        dialogLoading = mBuilder.create();
        dialogLoading.setCancelable(false);
        dialogLoading.setCanceledOnTouchOutside(false);
        dialogLoading.show();
    }

    public static void cancelLoadingDialog() {
        dialogLoading.dismiss();
    }

    /**
     * Download file pdf
     *
     * @param fileUrl   URL File
     * @param directory Directory where to save the file
     */
    public static void downloadFile(String fileUrl, File directory) {
        try {

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(directory);

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
     * Clean the stack of fragments that exists in execution
     *
     * @param activity activity context
     */
    public static void clearAddToBackStack(AppCompatActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }


    //    NAME DEVICE

    /**
     * Return the name of device
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    //JSON
    public static Map<String, String> makeJSONObject(String[] paramers, String[] values) {
        if (paramers.length != values.length) {
            return null;
        }

        Map<String, String> json = new HashMap<>();
        for (int i = 0; i < paramers.length; i++) {
            json.put(paramers[i], values[i]);
        }
        return json;
    }
}
