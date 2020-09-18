package application;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import application.DataBase.DBConnector;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BackgroundSet extends DBConnector{
	private Stage primaryStage;
	
	private ResultSet rs=null;
	private Blob image=null;
	public BackgroundSet(Stage primaryStage) {
		
		super();
		this.primaryStage=primaryStage;			
		tryConnect();
	}
	
	public void SetImage(ImageView imageView) {
		if(Connect)
		{
			new Thread(new Runnable() {
				private FileOutputStream fos;
				private InputStream is;
				int count=0;
				public void run()
				{
					while(primaryStage.isShowing())
					{
					String query1="Select count(*) as resc from UserImage1";
					try {
						pstmt=con.prepareStatement(query1);
						rs=pstmt.executeQuery();
						if(rs!=null)
						{
							rs.next();
							if(!(rs.getInt(1)<1))
							{
								count=rs.getInt(1);		
							}
						}						
						//개수를 알아내어 저장한뒤						
					} catch (SQLException e1) {
 
						e1.printStackTrace();
					}
					//알아낸 갯수로 랜덤실행
					String Query2="Select path from UserImage1";
					try {
						pstmt=con.prepareStatement(Query2,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);						
						rs=pstmt.executeQuery();		
						if(rs!=null&&!(count<1))
						{
							
							rs.absolute(((int)((Math.random()*count)))+1);						
							image=rs.getBlob("path");

							
							try {
								is=image.getBinaryStream();
						
								
								File file=new File("bg.jpg");
								fos=new FileOutputStream(file);
								
								

								
								RenderedImage ri=ImageIO.read(is);
								
								ImageIO.write(ri, "jpg", fos);
								is.close();
								fos.close();
								Image image2=new Image(file.toURI().toString());
								
								Platform.runLater(new Runnable() {
									public void run() {
									imageView.setFitHeight(primaryStage.getHeight());
										imageView.setFitWidth(primaryStage.getWidth());
									
										imageView.setPreserveRatio(true);
										imageView.setImage(image2);	
									}
								});
								
								
								
							
								
								
							} catch (IOException e) {
								// 입출력작업 에러
								e.printStackTrace();
							}	
							
						}
					
						
						
							
						} catch (SQLException e) {
							// sql작업 에러
							e.printStackTrace();
						}
						try {
							Thread.sleep(600000);
							
						} catch (InterruptedException e) {
							//쓰레드 가 잠드는 시간 
							e.printStackTrace();
						}
						
					}
					
					try {
						fos.close();
						is.close();
						pstmt.close();
						con.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}).start();
			
		}
	}
	

}
