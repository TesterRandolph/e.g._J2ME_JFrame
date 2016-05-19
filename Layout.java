import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Layout extends JFrame
{
	private BorderLayout layout;
	private JPanel ShowPanel;
	private JPanel InputPanel;
	private JPanel ButtonPanel;
	private JPanel ControlPanel;
	private JPanel panelall;
	private JLabel InputLabel, ShowLabel, MessageLabel;
	private JTextField InputTextField;
	private JButton StartButton, SubmitButton, ReinitButton, ExitButton, EndButton;
	private String InputString = "", OutputString = "";
	private int Counter[] = { 0, 0 }, RandomDefault[] = { -1, -1, -1, -1 }, GetInput[] = { -1, -1, -1, -1 };
	private int GuessCount = 0;
	
	public Layout ( )
	{
		super ( "猜數字" );
		
		layout = new BorderLayout ( 5, 5 );
		ShowPanel = new JPanel ( new GridLayout ( 2, 1 ) );
		InputPanel = new JPanel ( new GridLayout ( 2, 1 ) );
		ButtonPanel = new JPanel ( new GridLayout ( 1, 2 ) );
		ControlPanel = new JPanel ( new GridLayout ( 1, 2 ) );
		panelall = new JPanel ( new GridLayout ( 3, 1 ) );
		
		InputLabel = new JLabel ( "輸入：" );
		ShowLabel = new JLabel ( "結果：" );
		MessageLabel = new JLabel ( "" );
		InputTextField = new JTextField ( "" );
		StartButton = new JButton ( "開始" );
		SubmitButton = new JButton ( "確定" );
		ReinitButton = new JButton ( "重新開始" );
		ExitButton = new JButton ( "離開" );
		EndButton = new JButton ( "結束" );
		
//		buttons2.setVisible ( false );
		
		InputPanel.add ( InputLabel );
		InputPanel.add ( InputTextField );
		
		ShowPanel.add ( ShowLabel );
		ShowPanel.add ( MessageLabel );
		
		ButtonPanel.add ( SubmitButton );
		ButtonPanel.add ( ReinitButton );
		ButtonPanel.add ( ExitButton );
		
		ControlPanel.add ( StartButton );
		ControlPanel.add ( EndButton );
		
		panelall.add ( InputPanel );
		panelall.add ( ShowPanel );
		panelall.add ( ButtonPanel );
		
		Container c = getContentPane ( );
		c.setLayout ( layout );
		
		c.add ( ControlPanel, BorderLayout.CENTER );
		
		StartButtonHandler StartHandler = new StartButtonHandler( );
		StartButton.addActionListener ( StartHandler );
		ExitButtonHandler ExitHandler = new ExitButtonHandler( );
		ExitButton.addActionListener ( ExitHandler );
		ExitButtonHandler EndHandler = new ExitButtonHandler( );
		EndButton.addActionListener ( EndHandler );
		
		SubmitButtonHandler SubmitHandler = new SubmitButtonHandler( );
		SubmitButton.addActionListener ( SubmitHandler );
		ReinitButtonHandler ReinitHandler = new ReinitButtonHandler( );
		ReinitButton.addActionListener ( ReinitHandler );
		
		addWindowListener
		(
			new WindowAdapter ( )
			{
				public void windowClosing ( WindowEvent e )
				{
					System.exit ( 0 );
				}
			}
		);
		
		setSize ( 200, 100 );
		show ( );
	}
	
	private class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed ( ActionEvent event )
		{
			System.exit ( 0 );
		}
	}

	private class StartButtonHandler implements ActionListener
	{
		public void actionPerformed ( ActionEvent event )
		{
			Container c = getContentPane ( );
			c.setLayout ( layout );
			c.remove ( ControlPanel );
			c.add ( panelall, BorderLayout.CENTER );
			setSize ( 300, 200 );
			show ( );
			
			RandomDefault = getDefault ( );
			InputTextField.setText ("");
			InputTextField.requestFocus ( );
			MessageLabel.setText ("");
//			JOptionPane.showMessageDialog ( null, "亂數產生：" + RandomDefault[0] + RandomDefault[1] + RandomDefault[2] + RandomDefault[3], "Information", JOptionPane.INFORMATION_MESSAGE );
		}
	}
	
	private class SubmitButtonHandler implements ActionListener
	{
		public void actionPerformed ( ActionEvent event )
		{
			boolean tmpChecker = true;
			String tmpResult = "", tmpReward = "";
			
			InputString = InputTextField.getText ( ) .trim ( );
			
			if ( InputString.length ( ) == 4 )
			{
//				JOptionPane.showMessageDialog ( null, "輸入字串：" + InputString, "Information", JOptionPane.INFORMATION_MESSAGE );
				tmpChecker = getCheck ( );
				if ( tmpChecker )
				{
					GuessCount++;
					Counter = getCompare ( );
					tmpReward = getReward ( );

					if ( GuessCount <= 10 )
					{
						if( GuessCount < 10 && Counter [ 0 ] != 4 )
						{
//							JOptionPane.showMessageDialog ( null, "格式正確！！\n" + "你猜了 " + GuessCount + " 次！", "Information", JOptionPane.INFORMATION_MESSAGE );
							tmpResult = Counter [ 0 ] + " A " + Counter [ 1 ] + " B － " + tmpReward;
							MessageLabel.setText ( tmpResult );
							InputTextField.setText ("");
							InputTextField.requestFocus ( );
						}
						else if ( GuessCount == 10 )
						{
							if( Counter [ 0 ] == 4 )
							{
								JOptionPane.showMessageDialog ( null, "你猜了 " + GuessCount + " 次！\n" + tmpReward, "Information", JOptionPane.INFORMATION_MESSAGE );
							}
							else
							{
								JOptionPane.showMessageDialog ( null, "你猜了 " + GuessCount + " 次！\n" + tmpReward, "Information", JOptionPane.INFORMATION_MESSAGE );
							}
							
							int choice = JOptionPane.showConfirmDialog ( null, "是否開始新遊戲？", "Confirm", JOptionPane.YES_NO_OPTION );
							
							if ( choice == JOptionPane.OK_OPTION )
							{
								StartNewGame ( );
							}
							else
							{
								System.exit(0);
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog ( null, "發生錯誤，本程式即將關閉！！", "ERROR", JOptionPane.ERROR_MESSAGE );
						System.exit ( 0 );
					}
				}
				else
				{
					JOptionPane.showMessageDialog ( null, "格式錯誤！！", "ERROR", JOptionPane.ERROR_MESSAGE );
				}
			}
			else
			{
				JOptionPane.showMessageDialog ( null, "無法讀取錯誤字串！！", "ERROR", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	private class ReinitButtonHandler implements ActionListener
	{
		public void actionPerformed ( ActionEvent event )
		{
			StartNewGame ( );
			JOptionPane.showMessageDialog ( null, "可開始新遊戲了！", "Information", JOptionPane.INFORMATION_MESSAGE );
//			JOptionPane.showMessageDialog ( null, "重新產生：" + RandomDefault[0] + RandomDefault[1] + RandomDefault[2] + RandomDefault[3], "Information", JOptionPane.INFORMATION_MESSAGE );
		}
	}
	
	private void StartNewGame ( )
	{
		RandomDefault = getReinit ( );
		RandomDefault = getDefault ( );
		InputTextField.setText ("");
		InputTextField.requestFocus ( );
		MessageLabel.setText ("");
		GuessCount = 0;
	}
	
	private int [ ] getDefault ( )
	{
		int tmp = 0, tmpRandom [ ] = { -1, -1, -1, -1 };
		
		for ( int i = 0; i < 4; i++ )
		{
			tmp = ( int ) ( Math.random( ) * 9 );
			
			for ( int j = 0; j < 4; j++ )
			{
				if ( tmpRandom [ j ] < 0 )
				{
					tmpRandom [ j ] = tmp;
					j = 4;
				}
				else if ( tmpRandom [ j ] == tmp )
				{
					i--;
					j = 4;
				}
			}
		}
		
		return tmpRandom;
	}
	
	private int [ ] getReinit ( )
	{
		int tmpInit [ ] = { -1, -1, -1, -1 };
		return tmpInit;
	}
	
	private boolean getCheck ( )
	{
		boolean iChecker = true;
		int tmpInt = Integer.parseInt ( InputString );
		
		for ( int i = 3; i >= 0; i-- )
		{
			GetInput [ i ] = tmpInt % 10;
			tmpInt = ( tmpInt - GetInput [ i ] ) / 10;
		}
//		JOptionPane.showMessageDialog ( null, "重新產生：\n" + GetInput[0] + "\n" + GetInput[1] + "\n" + GetInput[2] + "\n" + GetInput[3] + "\n", "Information", JOptionPane.INFORMATION_MESSAGE );
		
		for ( int j = 0; j < 4; j++ )
		{
			for( int k = j + 1; k < 4; k++ )
			{
				if ( GetInput [ j ] == GetInput [ k ] )
				{
					iChecker = false;
					k = 4;
					j = 4;
				}
			}
		}
		
		return iChecker;
	}
	
	private int [ ] getCompare ( )
	{
		int tmpCounter [ ] = { 0, 0 };
		
		for ( int i = 0; i < 4; i++ )
		{
			for ( int j = 0; j < 4; j++ )
			{
				if ( RandomDefault [ i ] == GetInput [ j ] )
				{
					if ( i == j )
					{
						tmpCounter[ 0 ]++;
						j = 4;
					}
					else
					{
						tmpCounter[ 1 ]++;
						j = 4;
					}
				}
			}
		}
		
		return tmpCounter;
	}
	
	private String getReward ( )
	{
		String tmpReward = "";
		int tmpSum = 0;
		
		if ( GuessCount == 10 )
		{
			if ( Counter [ 0 ] == 4 )
			{
				tmpSum = 5;
			}
			else
			{
				tmpSum = 6;
			}
		}
		else
		{
			tmpSum = Counter [ 0 ] + Counter [ 1 ];
		}
		
		switch ( tmpSum )
		{
			case 0 :
				tmpReward = "不要亂猜好嘛！";
				break;
			case 1 :
				tmpReward = "邁出成功的第一步！";
				break;
			case 2 :
				tmpReward = "不錯嘛！有進步喔！";
				break;
			case 3 :
				tmpReward = "再加把勁！就快成功了！";
				break;
			case 4 :
				
				if ( Counter [ 0 ] == 4 )
				{
					tmpReward = "恭喜！終於成功了！";
				}
				else
				{
					tmpReward = "勝利就在眼前了！";
				}
				
				break;
			case 5 :
				tmpReward = "恭喜！終於成功了！";
				break;
			case 6 :
				tmpReward = "笨死了！居然猜不到！";
				break;
			default :
				tmpReward = "發生例外！！";
				break;
		}
		
		return tmpReward;
	}
}

public class GuessNumber
{
	public static void main ( String args [ ] ) throws Exception
	{
		Layout app = new Layout ( );
	}
}