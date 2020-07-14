package com.example.splashscreen.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splashscreen.R;
import com.example.splashscreen.model.Book;
import com.example.splashscreen.model.LoginResult;
import com.example.splashscreen.service.AppService;
import com.example.splashscreen.service.BookApiService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SecondFragment extends Fragment {


    public static final int PICK_IMAGE = 1;
    private String base64Image = "";
    private View view;
    EditText judul, penulis, penerbit, tahun, harga;
    Retrofit retrofit;
    Button btnChoose;
    Button btnUpload;


    public SecondFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contrainer, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second, contrainer, false);
        judul = view.findViewById(R.id.judul);
        penulis = view.findViewById(R.id.penulis);
        penerbit = view.findViewById(R.id.penerbit);
        tahun = view.findViewById(R.id.tahun);
        harga = view.findViewById(R.id.harga);
        btnChoose = view.findViewById(R.id.btnChoose);
        btnUpload = view.findViewById(R.id.btnUpload);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Choose Image", Toast.LENGTH_SHORT).show();
                imageChooser();
            }
        });
        return view;
    }

    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] b = baos.toByteArray();

        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            InputStream imageStream;
            String encodedImage = "";

            try {

                imageStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodeImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            base64Image = encodedImage;
        }
    }

    private void sendData(String judul, String penulis, String penerbit, String tahun,
                          String harga) {

        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(Integer.valueOf(tahun));
        book.setThumb(base64Image);

        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<LoginResult> result = apiService.insertNewBook(AppService.getToken(), book);
        result.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.body().isSuccess()) {
                    Log.e("TAG", "Berhasil Tambah Buku");
                } else {
                    Log.e("TAG", "Gagal Tambah Buku");
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });
    }
}