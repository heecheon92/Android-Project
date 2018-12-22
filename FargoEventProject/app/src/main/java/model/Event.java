package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Event implements Parcelable{
    private Integer id;
    private String title;
    private String image_url;
    private String event_description;
    private String start_date_time;
    private String end_date_time;
    private String location;
    private String featured;
    private List<SpeakerID> speakers;

    public List<SpeakerID> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<SpeakerID> speakers) {
        this.speakers = speakers;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public class SpeakerID implements Serializable
    {
        private Integer id;
        public SpeakerID(Integer id)
        {
            this.id = id;
        }
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public Event(Parcel parcel) {
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.image_url = parcel.readString();
        this.event_description = parcel.readString();
        this.start_date_time = parcel.readString();
        this.end_date_time = parcel.readString();
        this.location = parcel.readString();
        this.featured = parcel.readString();
        //List<SpeakerID> speakers = new ArrayList<SpeakerID>();
        //this.speakers = parcel.readList(speakers, null);
        //this.setSpeakers(parcel.readArrayList(null));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image_url);
        dest.writeString(event_description);
        dest.writeString(start_date_time);
        dest.writeString(end_date_time);
        dest.writeString(location);
        dest.writeString(featured);
        //dest.writeList(speakers);
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        public Event createFromParcel(Parcel source)
        {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };


    /*public ArrayList<Integer> getSpeakerID() {
        return speakers;
    }

    public void setSpeaker(ArrayList<Integer> speakers) {
        this.speakers = speakers;
    }*/


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(String end_date_time) {
        this.end_date_time = end_date_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }
}
