package kg.geektech.lesson2.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geektech.lesson2.App;
import kg.geektech.lesson2.R;
import kg.geektech.lesson2.data.models.Post;
import kg.geektech.lesson2.databinding.FragmentFormBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormFragment extends Fragment {
    private FragmentFormBinding binding;
    private static final int groupId = 39;
    private static final int userId = 5;
    NavController navController;
    private  int id;
    Post post;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        if (getArguments() != null){
            post = (Post) getArguments().getSerializable("key");
            binding.content.setText(post.getContent());
            binding.title.setText(post.getTitle());

        }

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.api.updatePost(String.valueOf(post.getId()),new Post(binding.content.getText().toString(),
                        binding.title.getText().toString())).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        navController.navigate(R.id.postFragment);
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            }
        });

        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);

        binding.btn.setOnClickListener(view1 ->
                App.api.createPost(new Post(binding.content.getText().toString().trim(),
                        binding.title.getText().toString().trim())).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        navController.navigate(R.id.postFragment);
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                })

        );


    }
}