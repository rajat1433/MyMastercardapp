package com.example.rajat.mymastercardapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DatastoreNotCreatedException;
import com.cloudant.sync.datastore.DocumentRevision;
import com.cloudant.sync.event.Subscribe;
import com.cloudant.sync.notifications.ReplicationCompleted;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/* Here we are fetching account info from Ibm Bluemix Cloudant services
int our local Datastore using datastore Manager
*/
public class Last10transactions extends AppCompatActivity {

    private DatastoreManager manager;
    private ProgressDialog progress ;
    private ArrayList<String> s= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last10transactions);


        BMSClient.getInstance().initialize(getApplicationContext(), BMSClient.REGION_SYDNEY);
        java.io.File path = getApplicationContext().getDir("datastores", android.content.Context.MODE_PRIVATE);
        android.util.Log.d("rev","hjhkhh");
        manager = DatastoreManager.getInstance(path);
        progress=new ProgressDialog(this);
        com.cloudant.sync.replication.Replicator pullReplicator;

        try {
            java.net.URI cloudantUri = new java.net.URI(getApplicationContext().getResources().getString(R.string.cloudantUrl1) + "/lasttentransactions");
            // Create the Datastore object you'll use in all of your Cloudant operations:
            android.util.Log.d("rev","hsadh");
            Datastore ds = manager.openDatastore("my_datastore4");
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

        //final TextView t= new TextView(this);
        final TextView t1=(TextView)findViewById(R.id.t1);
        final TextView t2=(TextView)findViewById(R.id.t2);
        final TextView t3=(TextView)findViewById(R.id.t3);
        final TextView t4=(TextView)findViewById(R.id.t4);
        final TextView t5=(TextView)findViewById(R.id.t5);
        final TextView t6=(TextView)findViewById(R.id.t6);
        final TextView t7=(TextView)findViewById(R.id.t7);
        final TextView t8=(TextView)findViewById(R.id.t8);
        final TextView t9=(TextView)findViewById(R.id.t9);
        final TextView t10=(TextView)findViewById(R.id.t10);
        //final RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        //lp.addRule(RelativeLayout.BELOW, R.id.imageView5);
        //t.setLayoutParams(lp);

        //t.setText("Your Balance is:");
        //t.setTextSize(40);

        //final RelativeLayout r=(RelativeLayout)findViewById(R.id.rl);
        //r.addView(t,lp);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress.isShowing());
                android.util.Log.d("fromth", s.get(0));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        t1.append(s.get(0));
                        t2.append(s.get(1));
                        t3.append(s.get(2));
                        t4.append(s.get(3));
                        t5.append(s.get(4));
                        t6.append(s.get(5));
                        t7.append(s.get(6));
                        t8.append(s.get(7));
                        t9.append(s.get(8));
                        t10.append(s.get(9));

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
            Datastore ds = manager.openDatastore("my_datastore4");
            Integer n = ds.getDocumentCount();
            android.util.Log.d("rev", n.toString());
            List<DocumentRevision> all = ds.getAllDocuments(0, n, true);

            android.util.Log.d("rev", all.toString());
            for (DocumentRevision rev : all) {
                Map<String, Object> m = rev.asMap();

                android.util.Log.d("rev","oops");
                if(m.containsKey("trans1")){
                    s.add((String) m.get("trans1"));
                }
                android.util.Log.d("rev","opps2");
                if(m.containsKey("trans2")){
                    s.add((String) m.get("trans2"));

                }
                android.util.Log.d("rev","opps3");
                if(m.containsKey("trans3")){
                    s.add((String) m.get("trans3"));
                }
                if(m.containsKey("trans4")){
                    s.add((String) m.get("trans4"));
                }
                if(m.containsKey("trans5")){
                    s.add((String) m.get("trans5"));
                }

                if(m.containsKey("trans6")){
                    s.add((String) m.get("trans6"));
                }
                if(m.containsKey("trans7")){
                    s.add((String) m.get("trans7"));
                }
                if(m.containsKey("trans8")){
                    s.add((String) m.get("trans8"));

                }
                if(m.containsKey("trans9")){
                    s.add((String) m.get("trans9"));
                }
                if(m.containsKey("trans10")){
                    s.add((String) m.get("trans10"));
                    break;
                }
            }
        }
        catch (DatastoreNotCreatedException e){
            android.util.Log.e("TAG", e.getMessage(), e);

        }
        //android.util.Log.d("rev", s.toString());
        android.util.Log.d("rev","hehehehehe");
        progress.dismiss();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
