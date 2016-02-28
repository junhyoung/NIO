package github.com.junhyoung.nio;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends Activity {

    boolean isFirst=true;
    double lat;
    double lng;
    TextView log;
    TextView add;
    String address;
    String curr;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        findlocate();
        add = (TextView) findViewById(R.id.address);
        log=(TextView)findViewById(R.id.log);
        findAddress(lat, lng);
    }
    public void copy(View v){
        add.setText(findAddress(lat, lng));
        draw(lat, lng);

    }

    public void find(View v) {
        address = add.getText().toString();
        List<Address> addr=null;
        Geocoder mCoder=new Geocoder(this);
        try{
            addr=mCoder.getFromLocationName(address,5);

        }catch (IOException e){}

        try{
            if(addr.size()==0){
                throw new Exception();
            }else {
                for (int i = 0; i < addr.size(); i++){
                    Address lating = addr.get(i);
                    lat=lating.getLatitude();
                    lng=lating.getLongitude();
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"결과가 없습니다",Toast.LENGTH_SHORT).show();
        }

        draw(lat,lng);
        findAddress(lat,lng);
    }

    public String findAddress(double lat1, double lng1) { //위도경도로 주소값 반환
        String temp="";
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder=new Geocoder(this, Locale.KOREA);
        List<Address> address;
        String currentLocationAddress;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat1, lng1, 3);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    // 주소
                    currentLocationAddress = address.get(0).getAddressLine(0).toString();

                    // 전송할 주소 데이터 (위도/경도 포함 편집)
                    bf.append(currentLocationAddress);
                    curr=bf.toString();
                    temp=curr.toString();
                    bf.append("\n").append("lat:").append(lat1).append(", ");
                    bf.append("lng:").append(lng1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        log.setText(bf.toString());
        return temp;
    }


    public void findlocate(){ //위도 경도 구해주는 함수
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();

                findAddress(lat, lng);
                if(isFirst) {
                    draw(lat, lng);
                    isFirst=false;
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                log.setText("onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                log.setText("onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                log.setText("onProviderDisabled");
            }
        };
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);




    }
    public void draw(double lat1, double lng1){
        LatLng locate=new LatLng(lat1,lng1);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Marker marker = map.addMarker(new MarkerOptions().position(locate).title("Here I am!"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locate, 15));

        map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
    }
}