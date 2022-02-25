package kg.geektech.lesson2.ui.post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kg.geektech.lesson2.App;
import kg.geektech.lesson2.R;
import kg.geektech.lesson2.data.models.Post;
import kg.geektech.lesson2.databinding.FragmentPostBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {
    private FragmentPostBinding binding;
    private Adapter adapter;
    NavHostFragment navHostFragment;
    private NavController controller;

    public PostFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new Adapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(
                inflater,
                container,
                false
        );

        controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPost(response.body());
                } else {
                    Log.e("TAG", "onResponseError:" + response.message());

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("ONF", "onFailure: " + t.getLocalizedMessage());

            }
        });
        binding.recycler.setAdapter(adapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.formFragment);

            }
        });

        adapter.setOnItemClick(new Adapter.onItemClick() {
            @Override
            public void onClick(Post post) {
                Bundle bundle= new Bundle();
                bundle.putSerializable("key", post);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.formFragment,bundle);
            }
            @Override
            public void onLongClick(Post post, int id) {

            }
        });
    }
}