package kg.geektech.lesson2.ui.post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kg.geektech.lesson2.App;
import kg.geektech.lesson2.R;
import kg.geektech.lesson2.data.models.Post;
import kg.geektech.lesson2.databinding.ItemPostBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.PostViewHolder> {
    private List<Post> posts = new ArrayList<>();
onItemClick onItemClick;


public void setPost(List<Post> post) {
        this.posts = post;
        notifyDataSetChanged();
    }

    public void setOnItemClick(Adapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding=ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
     holder.onBind(posts.get(position));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected class  PostViewHolder extends RecyclerView.ViewHolder{
        private ItemPostBinding binding;
        public  PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }

        public void onBind(Post post) {
            binding.tvUserId.setText("");
            binding.tvTitle.setText(post.getTitle());
            binding.tvDescription.setText(post.getContent());

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClick(post, post.getId());
                }
            });
            itemView.setOnLongClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Do you want delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.api.deletePost(post.getId()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                posts.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                onItemClick.onLongClick(post, post.getId());
                return true;
            });

        }



    }
    public interface onItemClick{
        void onClick(Post post, int id);
        void onLongClick(Post post, int id);

    }
}
