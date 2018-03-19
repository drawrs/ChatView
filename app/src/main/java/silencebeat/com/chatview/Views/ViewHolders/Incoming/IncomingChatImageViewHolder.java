package silencebeat.com.chatview.Views.ViewHolders.Incoming;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import silencebeat.com.chatview.Entities.Models.Comment;
import silencebeat.com.chatview.Modules.OnItemCommentClickListener;
import silencebeat.com.chatview.Supports.Utils.StaticVariable;
import silencebeat.com.chatview.databinding.ItemChatImageIncomingBinding;

/**
 * Created by Candra Triyadi on 15/03/2018.
 */

public class IncomingChatImageViewHolder extends RecyclerView.ViewHolder {

    ItemChatImageIncomingBinding binding;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    Calendar calendar = Calendar.getInstance();

    public IncomingChatImageViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void onBind(Context context, final Comment comment, final OnItemCommentClickListener listener){

        try {
            Date date = sdf.parse(comment.getNoteDate());
            calendar.setTime(date);
            String time = StaticVariable.parseDate(context, calendar);
            binding.txtTime.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Object url;

        if (comment.getFileUrl().contains("http")){
            url = comment.getFileUrl();
        }else{
            url = Uri.fromFile(new File(comment.getFileUrl()));
        }

        Glide.with(context)
                .load(url)
                .asBitmap()
                .override(100,100 )
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgPhoto);

        binding.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemCommentClicked(comment);
            }
        });
    }
}
