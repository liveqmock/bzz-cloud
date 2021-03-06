package com.bzz.cloud.storm;

import com.bzz.cloud.storm.bolt.MyBolt;
import com.bzz.cloud.storm.spout.MySpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

import java.util.HashMap;
import java.util.Map;

public class MyTopic {
	public static void main(String[] args)  {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout",new MySpout(),1);
		builder.setBolt("bolt",new MyBolt(),1).shuffleGrouping("spout");
		
		Map conf = new HashMap();
		conf.put(Config.TOPOLOGY_WORKERS,4);
		
		
		if(args.length > 0){
			try {
				StormSubmitter.submitTopology("Mytopology",conf,builder.createTopology());
			} catch (AlreadyAliveException e) {
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				e.printStackTrace();
			} catch (AuthorizationException e) {
				e.printStackTrace();
			}
		}else{
			LocalCluster localCluster = new LocalCluster();
			localCluster.submitTopology("Mytopology",conf,builder.createTopology());
		}
		
		
		
	}
}

