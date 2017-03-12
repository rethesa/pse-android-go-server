package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tarek on 10.02.17.
 * A simplified Appointment implementation for easy serialization
 */
import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("Appointment_class")
public class SimpleAppointment implements Serializable, Parcelable {
    private String name;
    private GpsObject destination;
    private long date;

    public SimpleAppointment() {
        // For objectmapper
    }

    public void setName(String name) {
        this.name = name;
    }
    public  void setDestination(GpsObject destination) {
        this.destination = destination;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public long getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public GpsObject getDestination() {
        return destination;
    }
    /**
	 * This is for serialization within android
	 */
	protected SimpleAppointment(Parcel in) {
        name = in.readString();
        destination = in.readParcelable(GpsObject.class.getClassLoader());
        date = in.readLong();
	}

	public static final Creator<SimpleAppointment> CREATOR = new Creator<SimpleAppointment>() {
        @Override
        public SimpleAppointment createFromParcel(Parcel source) {
        	return new SimpleAppointment(source);
        }

        @Override
        public SimpleAppointment[] newArray(int size) {
             return new SimpleAppointment[0];
		}
   };

   @Override
   public int describeContents() {
        return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(name);
       dest.writeParcelable(destination, flags);
       dest.writeLong(date);
   }
}
