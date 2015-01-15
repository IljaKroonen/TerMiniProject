package sharing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Picture;
import android.net.Uri;

public class SendPictureEmail implements ISendPicture {
    @Override
    public void sendPicture(Activity activity, Picture picture) {
        Intent sendIntent;

        sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Subject");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Test Text");
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + "qssfq"));
        sendIntent.setType("image/jpeg");

        activity.startActivity(Intent.createChooser(sendIntent, "Send picture data"));
    }
}
