package com.wrabbit.notesclone.drawview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import com.wrabbit.notesclone.R;

import java.io.OutputStream;

public class DrawFragment extends Fragment {

	private DrawView signView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		signView = (DrawView) inflater.inflate(R.layout.fragment_draw, null);
		
		return signView;
	}
	
	public void save(OutputStream outputStream){
		signView.save(outputStream);
	}
	


	
}