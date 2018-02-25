package core;

import java.io.File;

import org.junit.Test;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.Packet;

import junit.framework.TestCase;

public class ReadWriteTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testReadWrite() throws PcapNativeException, InterruptedException, NotOpenException {
		PRead read = new PRead("testdumpfile.pcap");
		read.open();
		final String file = "temp.pcap";
		final PcapDumper dumper = read.handle.dumpOpen(file);
		read.read(new PacketListener() {

			public void gotPacket(Packet packet) {
				try {
					dumper.dump(packet);
				} catch (NotOpenException e) {
					e.printStackTrace();
					fail("Not opened");
				}
			}
			
		});
		dumper.flush();
		read.close();
		dumper.close();
		
		File temp = new File(file);
		assertEquals(new File("testdumpfile.pcap").list(), temp.list());
		temp.delete();
	}
}
