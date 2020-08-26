package com.wrabbit.notesclone.utilities;

import android.view.View;

public interface OnClickListener {
	public void onClick(View view, int position);

	public void onLongClick(View view, int position);
}