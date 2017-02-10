package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=ObjectResponse.class, name="ObjectResponse_class")
	})
public class Response implements Parcelable {
	private boolean success;
	
	public Response(boolean success) {
		this.success = success;
	}

	public Response() {
	}

	protected Response(Parcel in) {
		success = in.readByte() != 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (success ? 1 : 0));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Response> CREATOR = new Creator<Response>() {
		@Override
		public Response createFromParcel(Parcel in) {
			return new Response(in);
		}

		@Override
		public Response[] newArray(int size) {
			return new Response[size];
		}
	};

	public boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
