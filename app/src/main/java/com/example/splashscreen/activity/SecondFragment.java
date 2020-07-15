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
import com.example.splashscreen.apiHelper.RetrofitUtility;
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
    private String base64Image;
    private String TAG = "Add Fragment";
    private View view;
    private ImageView imageView;
    private EditText judul, penulis, penerbit, tahun, harga;
    private Retrofit retrofit;
    private Button btnChoose;
    private Button btnUpload;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contrainer, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second, contrainer, false);

        initRetrofit();


        judul = view.findViewById(R.id.judul);
        penulis = view.findViewById(R.id.penulis);
        penerbit = view.findViewById(R.id.penerbit);
        imageView = view.findViewById(R.id.imgChoose);
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

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatejudul() | !validatepenulis() | !validatepenerbit() | !validatetahun() | !validateharga()) {
                    Toast.makeText(getActivity(), "Masukkan Data", Toast.LENGTH_SHORT).show();
                }else {
                    insertBook(
                            judul.getText().toString(),
                            penulis.getText().toString(),
                            penerbit.getText().toString(),
                            harga.getText().toString(),
                            tahun.getText().toString(),
                            base64Image
                    );
                }
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
                imageView.setImageURI(uri);
                imageStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodeImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            base64Image = encodedImage;
        }
    }

    private void insertBook(String judul, String penulis, String penerbit, String tahun,
                          String harga, String base64Image) {

        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(Integer.valueOf(tahun));
        book.setThumb(base64Image);

        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<Book> result = apiService.insertNewBook(AppService.getToken(), book);
        result.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {

                if (response.body().isSuccess()) {
                    Log.e("TAG", "Berhasil Tambah Buku");
                    Toast.makeText(getActivity(),"Add sukses",Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("TAG", "Gagal Tambah Buku");
                    Toast.makeText(getActivity(),"Add gagal" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {t.printStackTrace(); }
            });
    }

//        BookApiService apiService = retrofit.create(BookApiService.class);
//        Call<LoginResult> result = apiService.insertNewBook(AppService.getToken(), book);
//        result.enqueue(new Callback<LoginResult>() {
//            @Override
//            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
//                if (response.body().isSuccess()) {
//                    Log.e("TAG", "Berhasil Tambah Buku");
//                    Toast.makeText(getActivity(),"Add sukses",Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Log.e("TAG", "Gagal Tambah Buku");
//                    Toast.makeText(getActivity(),"Add gagal" + response.message(),Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResult> call, Throwable t) {t.printStackTrace(); }
//        });
//    }

    private void initRetrofit() {
        retrofit = RetrofitUtility.initializeRetrofit();
    }

    private boolean validatejudul() {

        String judulinput = judul.getText().toString().trim();

        if (judulinput.isEmpty()) {
            judul.setError("Field can't be empty");
            return false;
        } else {
            judul.setError(null);
            return true;
        }


    }

    private boolean validatepenerbit() {

        String penerbitinput = penerbit.getText().toString().trim();

        if (penerbitinput.isEmpty()) {
            penerbit.setError("Field can't be empty");
            return false;
        } else {
            penerbit.setError(null);
            return true;
        }


    }

    private boolean validatepenulis() {

        String penulisinput = penulis.getText().toString().trim();

        if (penulisinput.isEmpty()) {
            penulis.setError("Field can't be empty");
            return false;
        } else {
            penulis.setError(null);
            return true;
        }


    }

    private boolean validatetahun() {

        String tahuninput = tahun.getText().toString().trim();

        if (tahuninput.isEmpty()) {
            tahun.setError("Field can't be empty");
            return false;
        }  else {
            tahun.setError(null);
            return true;
        }


    }

    private boolean validateharga() {

        String hargainput = harga.getText().toString().trim();

        if (hargainput.isEmpty()) {
            harga.setError("Field can't be empty");
            return false;
        } else {
            harga.setError(null);
            return true;
        }


    }


}