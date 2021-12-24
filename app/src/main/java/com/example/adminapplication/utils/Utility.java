package com.example.adminapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;


import com.example.adminapplication.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Utility {

	public static List<User> searchForPerson(List<User> list, Editable s) {
		List<User> newList = new ArrayList<>();
		String ss = s.toString();
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);


				if(user.getEmail().contains(ss)){
					newList.add(user);
				}

		}
		return newList;
	}
}
