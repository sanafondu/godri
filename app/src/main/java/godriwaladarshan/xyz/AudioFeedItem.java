package godriwaladarshan.xyz;

/**
 * Created by SANA on 7/5/2016.
 */
public class AudioFeedItem {

    private String BhajanName = "";

    public AudioFeedItem(){}

    public AudioFeedItem(String BhajanName) {

        this. BhajanName = BhajanName;

    }

    public void setBhajanName(String BhajanName){

        this.BhajanName = BhajanName;

    }

    public String getBhajanName(){
      return BhajanName;
    }
}
