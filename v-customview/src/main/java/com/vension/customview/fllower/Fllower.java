package com.vension.customview.fllower;

import android.graphics.Bitmap;
import android.graphics.Path;

import java.io.Serializable;

public class Fllower implements Serializable {

	private static final long serialVersionUID = 1L;
	private Bitmap image;
	private float x;
	private float y;
	private Path path;
	private float value;

	public Bitmap getResId() {
		return image;
	}

	public void setResId(Bitmap img) {
		this.image = img;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Fllower [ x=" + x + ", y=" + y + ", path=" + path + ", value="
				+ value + "]";
	}

}
