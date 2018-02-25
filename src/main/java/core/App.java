package core;

import java.io.IOException;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

/**
 * Hello world!
 *
 */
public class App {
	
	static final int amount = 20;
	
	static final String dumpfile = "dumpfile.pcap";
	
	public static void packetHandle(Packet packet, PcapDumper dumper) throws PcapNativeException, NotOpenException {
		dumper.dump(packet);
		System.out.println("Packet received: " + packet.toString());
		handlePacket(packet);
	}
	
	public static void handlePacket(Packet packet) {
		
	}

	public static void a() throws IOException, PcapNativeException, InterruptedException, NotOpenException {
		final String filter = null;
		PcapNetworkInterface nif = new NifSelector().selectNetworkInterface();
		if (nif == null) {
			//System.err.println("nif is null!");
			System.exit(1);
		}
		final PcapHandle handle = nif.openLive(65536, PromiscuousMode.PROMISCUOUS, 10);
		if (filter != null && filter.length() != 0) {
			handle.setFilter(filter, BpfCompileMode.OPTIMIZE);
		}
		final PcapDumper dumper = handle.dumpOpen(dumpfile);
		PacketListener listener = new PacketListener() {
			public void gotPacket(Packet packet) {
				try {
					packetHandle(packet, dumper);
				} catch (PcapNativeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotOpenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		handle.loop(amount, listener);
		dumper.flush();
		dumper.close();
		Thread.sleep(2000);
	}
	
	public static void main(String[] args) {
		try {
			a();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PcapNativeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
