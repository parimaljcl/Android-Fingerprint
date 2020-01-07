package in.tvac.akshayejh.fingerprintauthapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Image;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    private  ImageView imageView;
    public FingerprintHandler(Context context) {

        this.context = context;
        imageView = (ImageView) ((Activity) context).findViewById(R.id.fingerprintImage);

    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an Auth Error. " + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth Failed. ", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
        this.update("You can now access the app.", true);
    }

    private void update(final String s, final boolean b) {
        Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.fingerprint);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                imageView.setImageResource(R.mipmap.action_done);
                //Toast.makeText(context, "Update-3000", Toast.LENGTH_SHORT).show();
                TextView paraLabel = (TextView) ((Activity) context).findViewById(R.id.paraLabel);
                paraLabel.setText(s);

                if (b == false) {
                    paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

                } else {
                    paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                }
            }
        }, 3000);

    }
}
