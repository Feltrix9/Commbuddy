package com.example.prueba1;

import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {

    private static final String TOKEN = "778147f8-141e-43f0-b4f1-6cc75998b199"; // Reemplaza con tu token

    public static String convertGsUrlToHttps(String gsUrl) {
        if (gsUrl.startsWith("gs://")) {
            // Eliminar el prefijo 'gs://'
            String path = gsUrl.substring(5);

            // Asegurarse de que el path no contenga barras iniciales adicionales
            if (path.startsWith("/")) {
                path = path.substring(1);
            }

            // Reemplazar '/' con '%2F' para URL encoding
            String encodedPath = Uri.encode(path, "/");

            // Construir la URL HTTPS
            String httpsUrl = "https://firebasestorage.googleapis.com/v0/b/commbuddy-ddcd7.appspot.com/o/" + encodedPath + "?alt=media";

            // Agregar el token si está disponible
            if (TOKEN != null && !TOKEN.isEmpty()) {
                httpsUrl += "&token=" + TOKEN;
            }
            System.out.println(httpsUrl);

            return httpsUrl;
        }
        System.out.println(gsUrl);

        return gsUrl; // Si no es un URL gs://, regresa el original
    }

    public static void loadImage(String gsUrl, ImageView imageView) {
        String httpsUrl = convertGsUrlToHttps(gsUrl);

        Picasso.get()
                .load(httpsUrl)
                .placeholder(R.drawable.placeholder_image) // Imagen que se muestra mientras carga
                .error(R.drawable.error_image) // Imagen en caso de error
                .into(imageView);
    }
}
