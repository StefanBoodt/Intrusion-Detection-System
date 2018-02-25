package core;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

public class PRead {
	
	private String file;
	
	protected PcapHandle handle;
	
	public PRead(String file) {
		this.file = file;
	}
	
	public void open() throws PcapNativeException {
		handle = Pcaps.openOffline(file);
	}
	
	public void close() {
		handle.close();
	}
	
	public void read() throws PcapNativeException, InterruptedException, NotOpenException {
		read(new PacketListener() {
			public void gotPacket(Packet packet) {
				App.handlePacket(packet);
			}
		});
	}
	
	public void read(PacketListener listener) throws PcapNativeException, InterruptedException, NotOpenException {
		handle.loop(-1, listener);
	}

}
