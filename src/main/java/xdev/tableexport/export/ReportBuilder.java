package xdev.tableexport.export;

/*-
 * #%L
 * XDEV BI Suite
 * %%
 * Copyright (C) 2011 - 2020 XDEV Software
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */


import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.PositionTypeEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;
import xdev.tableexport.config.ColumnBorder;
import xdev.tableexport.config.ColumnPadding;
import xdev.tableexport.config.ColumnStyle;
import xdev.tableexport.config.ContentColumn;
import xdev.tableexport.config.HeaderColumn;
import xdev.tableexport.config.PageProperties;
import xdev.tableexport.config.TemplateColumn;
import xdev.tableexport.config.TemplateConfig;
import xdev.vt.XdevBlob;
import xdev.vt.XdevClob;


/**
 * The {@link ReportBuilder} generates a {@link JasperReport} based on the
 * information of the {@link TemplateConfig}.
 * 
 * @author XDEV Software (FHAE)
 * 
 * @see TemplateConfig
 * @see ReportExporter
 * 
 */
public class ReportBuilder
{
	private final TemplateConfig			config;
	private final Set<JRDesignField>		fieldSet	= new HashSet<>();
	
	
	public ReportBuilder(final TemplateConfig tempConfig)
	{
		this.config = tempConfig;
	}
	
	
	private JRDesignBand initDetailBand()
	{
		final JRDesignBand detailBand = new JRDesignBand();
		detailBand.setHeight(TemplateConfig.DEFAULT_DETAIL_BAND_HEIGHT);
		detailBand.setSplitType(SplitTypeEnum.STRETCH);
		
		return detailBand;
	}
	
	
	private JRDesignBand initHeaderBand()
	{
		// Header
		final JRDesignBand headerBand = new JRDesignBand();
		headerBand.setHeight(TemplateConfig.DEFAULT_HEADER_BAND_HEIGHT);
		
		return headerBand;
	}
	
	
	private JasperDesign createDefaultDesign()
	{
		final JasperDesign jasperDesign = new JasperDesign();
		jasperDesign.setName("DefaultDesign");
		final PageProperties properties = this.config.getPageProperties();
		
		jasperDesign.setPageWidth(this.calcPageWidth(properties));
		jasperDesign.setColumnWidth(this.calcColumnsWidth());
		
		jasperDesign.setPageHeight(properties.getPageHeight());
		jasperDesign.setColumnSpacing(properties.getColumnSpacing());
		jasperDesign.setLeftMargin(properties.getLeftMargin());
		jasperDesign.setRightMargin(properties.getRightMargin());
		jasperDesign.setTopMargin(properties.getTopMargin());
		jasperDesign.setBottomMargin(properties.getBottomMargin());
		
		return jasperDesign;
	}
	
	
	private int calcColumnsWidth()
	{
		int width = 0;
		
		for(final TemplateColumn col : this.config.getColumns())
		{
			width += col.getWidth();
		}
		
		return width;
	}

	/***
	 * Calculated the page width including the margins.
	 * 
	 * @param properties
	 * 
	 * @return
	 */
	private int calcPageWidth(final PageProperties properties)
	{
		int width = 0;
		
		for(final TemplateColumn col : this.config.getColumns())
		{
			width += col.getWidth();
		}
		
		width += properties.getLeftMargin() + properties.getRightMargin();
		
		return width;
	}
	
	
	/**
	 * 
	 * @return
	 */
	private int calcMaxHeaderHeight()
	{
		
		final JLabel lbl = new JLabel("a");
		int maxHeight = 0;
		
		for(final TemplateColumn col : this.config.getColumns())
		{
			final ColumnStyle style = col.getHeaderColumn().getStyle();
			final Font font = style.getFont();
			lbl.setFont(font);
			//get the normal height of the label
			int lblHeigh = (int)lbl.getPreferredSize().getHeight();
			//add the column border width
			lblHeigh+= style.getColBorder().getLineWidth()*2;
			//Add padding
			lblHeigh+= style.getColumnPadding().getTopWidth() + style.getColumnPadding().getBottomWidth();
			
			if(lblHeigh > maxHeight)
			{
				maxHeight = lblHeigh;
			}
		}
		
		return maxHeight;
	}
	
	private int calcMaxContentHeight()
	{
		
		final JLabel lbl = new JLabel("a");
		int maxHeight = 0;
		
		for(final TemplateColumn col : this.config.getColumns())
		{
			final ColumnStyle style = col.getContentColumn().getStyle();
			final Font font = style.getFont();
			lbl.setFont(font);
			int lblHeigh = (int)lbl.getPreferredSize().getHeight();
			//Add border width
			lblHeigh+= style.getColBorder().getLineWidth()*2;
			//Add padding
			lblHeigh+= style.getColumnPadding().getTopWidth() + style.getColumnPadding().getBottomWidth();
			
			if(lblHeigh > maxHeight)
			{
				maxHeight = lblHeigh;
			}
		}
		
		return maxHeight;
	}
	
	
	private void createTemplateFields(final JasperDesign jasperDesign) throws ExportException
	{
		JRDesignField field;
		
		for(final TemplateColumn col : this.config.getColumns())
		{
			field = new JRDesignField();
			field.setName(col.getContentColumn().getFieldName());
			this.chooseValueClass(col,field);
			
			try
			{
				jasperDesign.addField(field);
				this.fieldSet.add(field);
			}
			catch(final JRException e)
			{
				throw new ExportException("error during add the field "
						+ col.getContentColumn().getFieldName(),e);
			}
		}
		
	}

	
	private JRDesignField chooseValueClass(final TemplateColumn col, final JRDesignField field)
	{
		final Class<?> valueClass = col.getContentColumn().getColumnValueClass();
		
		if(valueClass.isAssignableFrom(byte[].class) || valueClass.isAssignableFrom(XdevBlob.class) || valueClass.isAssignableFrom(XdevClob.class))
		{
			field.setValueClass(String.class);
		}
		else
		{
			field.setValueClass(valueClass);
		}
		
		return field;
	}
	
	
	private void createHeaderAndContent(final JRDesignBand headerBand, final JRDesignBand detailBand)
	{
		JRDesignStaticText headerLabel;
		JRDesignStaticText emptyHeaderLabel;
		JRDesignTextField textField;
				
		final int headerLabelHeight = this.calcMaxHeaderHeight();
		headerBand.setHeight(headerLabelHeight);
		
		final int contentLabelHeight = this.calcMaxContentHeight();
		detailBand.setHeight(contentLabelHeight);
				
		final boolean createHeader = this.config.hasAnyHeader();
		
		int x = 0;
		
		for(final TemplateColumn col : this.config.getColumns())
		{
			// Header is created
			if(createHeader)
			{
				// If this column has a header the JRDesignStaticText get the
				// propertys of the Column
				if(col.hasHeaderColumn())
				{
					final HeaderColumn headerColumn = col.getHeaderColumn();
					// Build label and set x / y
					headerLabel = new JRDesignStaticText();
					headerLabel.setX(x);
					headerLabel.setWidth(col.getWidth());
					headerLabel.setY(TemplateConfig.DEFAULT_COMPONENT_Y_POSITION);
					
					headerLabel.setHeight(headerLabelHeight);
					this.setStlyeForTextField(headerLabel,headerColumn.getStyle());
					this.prepareTextfieldWithBorder(headerLabel,headerColumn.getStyle());
					this.prepareTextfieldPadding(headerLabel,headerColumn.getStyle());
					
					headerLabel.setPositionType(PositionTypeEnum.FLOAT);

					
					// Get the Property
					headerLabel.setText(headerColumn.getProperty());
					headerBand.addElement(headerLabel);
				}
				else
				{
					// an empty label must be added to complete the layout
					// Build label and set x / y
					emptyHeaderLabel = new JRDesignStaticText();
					emptyHeaderLabel.setX(x);
					emptyHeaderLabel.setWidth(col.getWidth());
					headerBand.addElement(emptyHeaderLabel);
				}
			}
			
			final ContentColumn contentColumn = col.getContentColumn();
			
			textField = new JRDesignTextField();
			textField.setX(x);
			textField.setWidth(col.getWidth());
			textField.setY(TemplateConfig.DEFAULT_COMPONENT_Y_POSITION);
			textField.setHeight(contentLabelHeight);
			
			this.setStlyeForTextField(textField,contentColumn.getStyle());
			textField.setPattern(contentColumn.getProperty());
			
			// box tag properties
			this.prepareTextfieldWithBorder(textField,contentColumn.getStyle());
			this.prepareTextfieldPadding(textField,contentColumn.getStyle());
			
			textField.setExpression(this.buildExpression(contentColumn));
			
			textField.setPositionType(PositionTypeEnum.FLOAT);
			
			if(this.config.isBlankWhenNullValue())
			{
				textField.setBlankWhenNull(true);
			}
			detailBand.addElement(textField);
			
			x += col.getWidth();
		}
		
	}
	
	private void setStlyeForTextField(final JRDesignTextElement txtField, final ColumnStyle style)
	{
		txtField.setBackcolor(style.getBackground());
		txtField.setForecolor(style.getForeground());
		// Font
		final Font f = style.getFont();
		txtField.setFontName(f.getName());
		txtField.setFontSize(Float.valueOf(f.getSize()));
		txtField.setBold(Boolean.valueOf(f.isBold()));
		txtField.setItalic(Boolean.valueOf(f.isItalic()));
		txtField.setHorizontalTextAlign(style.getHorizontalAlignment().getHorizontalTextAlignEnum());
		
		
		if(!style.getBackground().equals(Color.WHITE))
		{
			txtField.setMode(ModeEnum.OPAQUE);
		}
	}
	
	
	private void prepareTextfieldWithBorder(final JRDesignTextElement textField, final ColumnStyle style)
	{
		final ColumnBorder border = style.getColBorder();
		if(border == null)
		{
			return;
		}
		
		textField.getLineBox().getPen().setLineWidth(border.getLineWidth());
		textField.getLineBox().getPen().setLineColor(border.getLineColor());
		textField.getLineBox().getPen().setLineStyle(border.getLineStyle().getLineStyleEnum());
	}
	
	private void prepareTextfieldPadding(final JRDesignTextElement textField, final ColumnStyle style)
	{
		final ColumnPadding colPadding = style.getColumnPadding();
		final JRLineBox lineBox = textField.getLineBox();
		
		
		lineBox.setTopPadding(colPadding.getTopWidth());
		lineBox.setRightPadding(colPadding.getRightWidth());
		lineBox.setLeftPadding(colPadding.getLeftWidth());
		lineBox.setBottomPadding(colPadding.getBottomWidth());
	}
	
	
	private JRDesignExpression buildExpression(final ContentColumn column)
	{
		final JRDesignExpression expression = new JRDesignExpression();
		expression.setText("$F{" + column.getFieldName() + "}");
		return expression;
	}
	
	
	/**
	 * 
	 * Assemble and compile a {@link JasperReport} based on the information of
	 * the {@link TemplateConfig} object.
	 * 
	 * @return the compiled {@link JasperReport}
	 * @throws ExportException
	 */
	public JasperReport assembleReport() throws ExportException
	{
		try
		{
			final JasperDesign jasperDesign = this.createDefaultDesign();
			this.createTemplateFields(jasperDesign);
			
			final JRDesignBand headerBand = this.initHeaderBand();
			final JRDesignBand detailBand = this.initDetailBand();
			
			this.createHeaderAndContent(headerBand,detailBand);
			
			((JRDesignSection)jasperDesign.getDetailSection()).addBand(detailBand);
			if(this.config.hasAnyHeader())
			{
				jasperDesign.setTitle(headerBand);
			}
						
			return JasperCompileManager.compileReport(jasperDesign);
		}
		catch(final Exception e)
		{
			throw new ExportException(e);
		}
	}
	
}
