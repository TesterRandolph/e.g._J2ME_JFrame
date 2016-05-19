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
		super ( "�q�Ʀr" );
		
		layout = new BorderLayout ( 5, 5 );
		ShowPanel = new JPanel ( new GridLayout ( 2, 1 ) );
		InputPanel = new JPanel ( new GridLayout ( 2, 1 ) );
		ButtonPanel = new JPanel ( new GridLayout ( 1, 2 ) );
		ControlPanel = new JPanel ( new GridLayout ( 1, 2 ) );
		panelall = new JPanel ( new GridLayout ( 3, 1 ) );
		
		InputLabel = new JLabel ( "��J�G" );
		ShowLabel = new JLabel ( "���G�G" );
		MessageLabel = new JLabel ( "" );
		InputTextField = new JTextField ( "" );
		StartButton = new JButton ( "�}�l" );
		SubmitButton = new JButton ( "�T�w" );
		ReinitButton = new JButton ( "���s�}�l" );
		ExitButton = new JButton ( "���}" );
		EndButton = new JButton ( "����" );
		
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
//			JOptionPane.showMessageDialog ( null, "�üƲ��͡G" + RandomDefault[0] + RandomDefault[1] + RandomDefault[2] + RandomDefault[3], "Information", JOptionPane.INFORMATION_MESSAGE );
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
//				JOptionPane.showMessageDialog ( null, "��J�r��G" + InputString, "Information", JOptionPane.INFORMATION_MESSAGE );
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
//							JOptionPane.showMessageDialog ( null, "�榡���T�I�I\n" + "�A�q�F " + GuessCount + " ���I", "Information", JOptionPane.INFORMATION_MESSAGE );
							tmpResult = Counter [ 0 ] + " A " + Counter [ 1 ] + " B �� " + tmpReward;
							MessageLabel.setText ( tmpResult );
							InputTextField.setText ("");
							InputTextField.requestFocus ( );
						}
						else if ( GuessCount == 10 )
						{
							if( Counter [ 0 ] == 4 )
							{
								JOptionPane.showMessageDialog ( null, "�A�q�F " + GuessCount + " ���I\n" + tmpReward, "Information", JOptionPane.INFORMATION_MESSAGE );
							}
							else
							{
								JOptionPane.showMessageDialog ( null, "�A�q�F " + GuessCount + " ���I\n" + tmpReward, "Information", JOptionPane.INFORMATION_MESSAGE );
							}
							
							int choice = JOptionPane.showConfirmDialog ( null, "�O�_�}�l�s�C���H", "Confirm", JOptionPane.YES_NO_OPTION );
							
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
						JOptionPane.showMessageDialog ( null, "�o�Ϳ��~�A���{���Y�N�����I�I", "ERROR", JOptionPane.ERROR_MESSAGE );
						System.exit ( 0 );
					}
				}
				else
				{
					JOptionPane.showMessageDialog ( null, "�榡���~�I�I", "ERROR", JOptionPane.ERROR_MESSAGE );
				}
			}
			else
			{
				JOptionPane.showMessageDialog ( null, "�L�kŪ�����~�r��I�I", "ERROR", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	private class ReinitButtonHandler implements ActionListener
	{
		public void actionPerformed ( ActionEvent event )
		{
			StartNewGame ( );
			JOptionPane.showMessageDialog ( null, "�i�}�l�s�C���F�I", "Information", JOptionPane.INFORMATION_MESSAGE );
//			JOptionPane.showMessageDialog ( null, "���s���͡G" + RandomDefault[0] + RandomDefault[1] + RandomDefault[2] + RandomDefault[3], "Information", JOptionPane.INFORMATION_MESSAGE );
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
//		JOptionPane.showMessageDialog ( null, "���s���͡G\n" + GetInput[0] + "\n" + GetInput[1] + "\n" + GetInput[2] + "\n" + GetInput[3] + "\n", "Information", JOptionPane.INFORMATION_MESSAGE );
		
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
				tmpReward = "���n�òq�n���I";
				break;
			case 1 :
				tmpReward = "�ڥX���\���Ĥ@�B�I";
				break;
			case 2 :
				tmpReward = "�������I���i�B��I";
				break;
			case 3 :
				tmpReward = "�A�[��l�I�N�֦��\�F�I";
				break;
			case 4 :
				
				if ( Counter [ 0 ] == 4 )
				{
					tmpReward = "���ߡI�ש󦨥\�F�I";
				}
				else
				{
					tmpReward = "�ӧQ�N�b���e�F�I";
				}
				
				break;
			case 5 :
				tmpReward = "���ߡI�ש󦨥\�F�I";
				break;
			case 6 :
				tmpReward = "�¦��F�I�~�M�q����I";
				break;
			default :
				tmpReward = "�o�ͨҥ~�I�I";
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