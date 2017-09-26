package com.example.rajat.mymastercardapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DatastoreNotCreatedException;
import com.cloudant.sync.datastore.DocumentRevision;
import com.cloudant.sync.event.Subscribe;
import com.cloudant.sync.notifications.ReplicationCompleted;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/* Here we are fetching account info from Ibm Bluemix Cloudant services
int our local Datastore using datastore Manager
*/
public class Net_Balance extends AppCompatActivity {
    private  DatastoreManager manager;
    private ProgressDialog progress ;
    private ArrayList<String> s= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net__balance);
        BMSClient.getInstance().initialize(getApplicationContext(), BMSClient.REGION_SYDNEY);
        java.io.File path = getApplicationContext().getDir("datastores", android.content.Context.MODE_PRIVATE);

        manager = DatastoreManager.getInstance(path);
        progress=new ProgressDialog(this);
        com.cloudant.sync.replication.Replicator pullReplicator;

        try {
            java.net.URI cloudantUri = new java.net.URI(getApplicationContext().getResources().getString(R.string.cloudantUrl1) + "/netbalance");
            // Create the Datastore object you'll use in all of your Cloudant operations:
            Datastore ds = manager.openDatastore("my_datastore2");
            // At this point, you may wish to create pull and push replicators for bi-directional sync.  It is up
            // to you, the developer, to program the interaction between the Cloudant database and the mobile application.
            // A simple example follows.  The example does not include a countdown latch, does not show how to
            // subscribe to sync events, and ignores sync errors.
//            if(ds.getDocumentCount()!=0){
//                Integer n = ds.getDocumentCount();
//                android.util.Log.d("rev", n.toString());
//                List<DocumentRevision> all = ds.getAllDocuments(0, n, true);
//                for(DocumentRevision rev : all ){
//                    ds.deleteDocumentFromRevision(rev);
//                }
//
//            }
            // Create and run the pull replicator
            pullReplicator = com.cloudant.sync.replication.ReplicatorBuilder.pull().from(cloudantUri).to(ds).build();
            pullReplicator.getEventBus().register(this);
            pullReplicator.start();
            android.util.Log.d("rev2", pullReplicator.getState().toString());

            progress.setTitle("Loading");
            progress.setMessage("FetchingData");
            progress.setCancelable(false);
            progress.show();


            // Create and run the push replicator
            //com.cloudant.sync.replication.Replicator pushReplicator = com.cloudant.sync.replication.ReplicatorBuilder.push().to(cloudantUri).from(ds).build();
            //pushReplicator.start();

        } catch (java.net.URISyntaxException e) {
            android.util.Log.e("TAG", e.getMessage(), e);
        } catch (DatastoreNotCreatedException e) {
            android.util.Log.e("TAG", e.getMessage(), e);
            android.util.Log.d("rev",e.getMessage());
        }
        catch (Exception e){
            android.util.Log.d("rev",e.toString());
        }

        final TextView t=(TextView)findViewById(R.id.tv1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress.isShowing());
                android.util.Log.d("fromth", s.get(0));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        t.append(s.get(0));
                    }
                });
            }
        }).start();
    }

    @Subscribe
    public void completed(ReplicationCompleted rc){
        android.util.Log.d("rev", "rep completed");
        android.util.Log.d("rev2", rc.replicator.getState().toString());

        try {
            Datastore ds = manager.openDatastore("my_datastore2");
            Integer n = ds.getDocumentCount();
            android.util.Log.d("rev", n.toString());
            List<DocumentRevision> all = ds.getAllDocuments(0, n, true);

            android.util.Log.d("rev", all.toString());
            for (DocumentRevision rev : all) {
                Map<String, Object> m = rev.asMap();


                if(m.containsKey("net")){
                    s.add((String) m.get("net"));
                    break;
                }

            }
        }
        catch (DatastoreNotCreatedException e){
            android.util.Log.e("TAG", e.getMessage(), e);

        }
        android.util.Log.d("rev", s.toString());
        progress.dismiss();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
