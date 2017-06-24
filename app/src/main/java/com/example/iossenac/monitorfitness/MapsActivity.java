package com.example.iossenac.monitorfitness;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.iossenac.monitorfitness.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.clustering.ClusterManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static android.R.attr.max;
import static android.R.attr.positiveButtonText;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationClient;

    private final int MY_REQUEST_CODE = 10;

    private LocationCallback locationCallback;
    private Location lastLocation;
    private double[][] positions = {
            {-30.038645, -51.228211},
            {-30.037084, -51.228126},
            {-30.037790, -51.230529},
            {-30.037642, -51.225722},
            {-30.036527, -51.226752},
            {-30.036007, -51.231773},
            {-30.038533, -51.232889},
            {-30.035338, -51.228040},
            {-30.035450, -51.235335},
            {-30.037270, -51.236065},
            {-30.039648, -51.237310},
            {-30.035858, -51.220487},
            {-30.038496, -51.218641},
            {-30.034298, -51.221474},
            {-30.035933, -51.222032}};

    private ClusterManager<MyItem> mClusterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //pega o mapa assincronamente. Nos temos que passar uma listener de callback
        mapFragment.getMapAsync(this);

        //recupera o cliente do LocationService, que acompanha as
        //mudanças de geolocalização do device
        mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    lastLocation = location;
                }
                //código que move a câmera do mapa para a nova location
                moveCameraToNewPosition();
            };
        };

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        for(int i =0; i < positions.length; i++){
           // MarkerOptions opt = new MarkerOptions();
            //opt.position(new LatLng(positions[i][0],positions[i][1]));
            //mMap.addMarker(opt);

            MyItem myItem = new MyItem(positions[i][0], positions[i][1]);
            mClusterManager.addItem(myItem);

        }

        List<LatLng> steps = PolyUtil.decode("ncivDlmrwHwv@fGwFhAsMBeNeSgrA}i@qlAuXgvBwj@wQ`H}gB|PyjBl[yz@vv@ug@t{AezAppAao@fCaeAk[io@o`@rFuAeXbs@ey@hn@ua@pRmTpb@{_Af{@}p@fhCqX`eBs`AbkC}MrlBsYjlAo]vyFtPzbF`HlbBaIfcAkGtkBKdj@gUtq@e\\dVuN|h@q\\jdAkHho@_]~^yi@zpA}q@plAg}@rmCgcA|_BoGxSo\\bMaO`WyEtq@gfAz`Bs[da@es@pXsy@zaAgm@jXm[fdAgBb{AO`h@}RhYgc@z~@mk@~oC_aA~bA}pA``Am|@njA}S|p@mnAz|@sWfPig@`Did@fNaPbf@wTpdBctAvtAyYJ__@ru@_iAvwB{d@l~@kRpbAcd@|tAog@toAijAdzBag@nY_{@d_Acf@j`AwsAxi@_q@hc@eWfRwx@tNc_@`Je|@yI_fArOej@jNsT~h@_g@ro@yn@mOsbCa]syAajAqe@c^ix@kpAqqB_eDciAf`@mh@xAku@fa@ww@mGan@mBgv@}qAapAo`@mdBxMcaA_bB_cAq~@ue@qb@oe@jNwYdP_a@yHa]nPmn@r|@k|@pUy_Bo|@et@uUyk@|m@e_@pt@iu@|\\iW_J}`@agA}]oc@vH{e@wV}l@e_@cPar@lZow@cw@kk@oOo^nA_r@mRy`@cEwQeLcRdFqc@~UcVnLov@{[cRyQuaAtC_bAyX}z@_x@ye@zEig@|^st@jSqWdPor@aE{VmDsWdXePeGsk@yj@}[`TpCzWed@uPsK{HxCuUbDyV}c@{V}TfH{[aK_m@zEom@hU}Mve@g|@wQuR|Zsy@vR_U`_@mCjUsT`JeHdP{JtOsS`L}KgK{mBl`@_tBlqB{{Abt@cbBfs@_e@hAumAaJml@oI_x@xe@yg@`KeRcFmOp[{w@l_@wx@z@oeAmNaa@qy@gUw@_r@nnBiI~a@aa@tj@kd@j@sfA_tAa_@kWoLzLsXvJwRfa@i`@xg@{i@`U{ZbRwm@{Bmo@bZyXza@mt@pBml@~Og_A~G{p@xDqc@pR}m@_Eej@g`@s[wLeh@`DydAkE_i@mEu[jUy~@|Tu^BwQkD}b@`OsRf`@{y@fFdCirAkXik@y}@uvAhOku@e`@wk@mk@uLia@iv@uWg\\uRolAqXwe@cw@qj@zIab@wI_JxMeVVsLoVuXy]_c@}cAcCit@oAy`@mBeK]cSoSi`@pSca@pVqQ{G}\\zGuPqo@uJcEaNfIob@oF_kAcVqkA_Jwt@vLeFq^sCsc@");
        LatLng inicio = steps.get(0);
        for (int i = 1; i < steps.size(); i++){
            PolylineOptions lineOpt = new PolylineOptions().add(inicio, steps.get(i));
            mMap.addPolyline(lineOpt);
            inicio = steps.get(i);
        }

        MarkerOptions optFinal = new MarkerOptions();
        optFinal.position(inicio);
        mMap.addMarker(optFinal);

        PolygonOptions polygonOptions =
                new PolygonOptions();

        LatLng latLng1 = new LatLng(-30.012608, -51.118992);
        LatLng latLng2 = new LatLng(-30.012487, -51.120301);
        LatLng latLng3 = new LatLng(-30.013964, -51.120548);
        LatLng latLng4 = new LatLng(-30.014191, -51.119194);
        polygonOptions.add(latLng1, latLng2, latLng3, latLng4);

        polygonOptions.fillColor(Color.argb(125,255,0,0));
        polygonOptions.strokeColor(Color.BLACK);
        polygonOptions.strokeWidth(1);
        mMap.addPolygon(polygonOptions);

        mMap.setInfoWindowAdapter(new CustoInfoWindowAdapter(this));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Se o usuário retirou a permissão pro ACCESS_FINE_LOCATION
            //nós requisitamos novamente;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_REQUEST_CODE);
        } else {
            //se a permissão para o ACCESS_FINE_LOCATION ainda existe
            //chama o método que pega a última localização conhecida

        }
    }


    public void moveCameraToNewPosition(){
        if (lastLocation != null) {
            //LatLng: classe que encapsula apenas latitude e longitude

            //atualização da câmera. A CameraUpdateFactory fabrica
            //instância desta classe, com seus métodos estáticos
            //temos várias opções de atualização da câmera

            LatLng latLng = new LatLng(
                    lastLocation.getLatitude(),
                    lastLocation.getLongitude()
            );
            CameraUpdate cameraUpdate =
                    CameraUpdateFactory.newLatLngZoom(latLng, 18);
            mMap.animateCamera(cameraUpdate);
        }
    }




}

