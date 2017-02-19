package edu.kit.pse.bdhkw.server.test;

import java.io.File;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import org.jboss.logging.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.management.MysqldResourceI;
import com.mysql.management.driverlaunched.ServerLauncherSocketFactory;

import edu.kit.pse.bdhkw.server.controller.HibernateSessionFactoryListener;

public class EmbeddedMysqlDataSource extends MysqlDataSource
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
  private int port;
  private String sock;
  private String url;
  private File basedir;
  private File datadir;
  private Connection connection;

  private static Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);

  public static EmbeddedMysqlDataSource getInstance()
  {
    EmbeddedMysqlDataSource dataSource = null;
    try {
      dataSource = new EmbeddedMysqlDataSource( 4000 );
      dataSource.setUrl( dataSource.getEmbeddedUrl() );
      dataSource.setUser( "root" );
      dataSource.setPassword( "" );
    } catch( Exception e2 ) {
      dataSource = null;
      logger.info( "Could not create embedded server.  Skipping tests. (%s)", e2.getMessage(), e2 );
      e2.printStackTrace();
    }
    return dataSource;
  }

  public static void shutdown( EmbeddedMysqlDataSource ds )
  {
    try {
      ds.shutdown();
    } catch( IOException e ) {
      logger.info( "Could not shutdown embedded server. (%s)", e.getMessage() ,e);
      e.printStackTrace();
    }
  }

  public EmbeddedMysqlDataSource( int port ) throws IOException
  {
    super();
    this.port = port;
    sock = "sock" + System.currentTimeMillis();

    // We need to set our own base/data dirs as we must
    // pass those values to the shutdown() method later
    basedir = File.createTempFile( "mysqld-base", null );
    datadir = File.createTempFile( "mysqld-data", null );

    // Wish there was a better way to make temp folders!
    basedir.delete();
    datadir.delete();
    basedir.mkdir();
    datadir.mkdir();
    basedir.deleteOnExit();
    datadir.deleteOnExit();

    StringBuilder sb = new StringBuilder();
    sb.append( String.format( "jdbc:mysql:mxj://localhost:%d/test", port ));
    sb.append( "?createDatabaseIfNotExist=true" );
    sb.append( "&server.basedir=" ).append( basedir.getPath() );
    sb.append( "&server.datadir=" ).append( datadir.getPath() );
    url = sb.toString();
  }

  public String getEmbeddedUrl()
  {
    return url;
  }

  @Override
  protected java.sql.Connection getConnection( Properties props ) throws SQLException
  {
    if( connection == null ) {
      props.put( MysqldResourceI.PORT, String.valueOf( port ));
      props.put( MysqldResourceI.SOCKET, sock );
      props.put( MysqldResourceI.BASEDIR, basedir.getPath() );
      props.put( MysqldResourceI.DATADIR, datadir.getPath() );
      connection = super.getConnection( props );
    }
    return connection;
  }

  public void shutdown() throws IOException
  {
    ServerLauncherSocketFactory.shutdown( basedir, datadir );
  }
}