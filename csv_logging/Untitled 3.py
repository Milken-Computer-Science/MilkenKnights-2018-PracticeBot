import plotly.plotly as py
import plotly.graph_objs as go
from plotly.offline import download_plotlyjs, init_notebook_mode, plot, iplot
import numpy as np
import numpy as np
import pandas as pd

lf = pd.read_csv('Pathleft.csv')
rf = pd.read_csv('Pathright.csv')



lpathtrace = go.Scatter(x=lf['X'], y=lf['Y'], mode='lines', name='Left Path')
rpathtrace = go.Scatter(x=rf['X'], y=rf['Y'], mode='lines', name='Right Path')

pathlayout = go.Layout(title='Robot Path', plot_bgcolor='rgb(230, 230,230)', images= [dict(
			source= "https://raw.githubusercontent.com/Team254/FRC-2017-Public/master/cheesy_path/field.png",
			xref= "X",
			yref= "Y",
			xanchor="left",
			yanchor="top",
			x= 0,
			y= -17.5,
			sizex= 52.5,
			sizey= 30.5,
			sizing= "contain",
			opacity= 1,
			visible= True,
			layer= "above")])
			
			
			
pathfig = go.Figure(data=[lpathtrace,rpathtrace], layout=pathlayout)

plot(pathfig, filename='Robot Path')

trace1= go.Scatter(x=[0,0.5,1,2,2.2],y=[1.23,2.5,0.42,3,1])
layout= go.Layout(images= [dict(
			source= "https://images.plot.ly/language-icons/api-home/python-logo.png",
			visible= True,
			layer= "above",
			xanchor="left",
			yanchor="top",
			sizex= 52.5,
			sizey= 30.5,
			sizing= "contain",
			opacity= 1,
			xref= "X",
			x= 0,
			yref= "Y",
			y= -17.5	
			)])
fig=go.Figure(data=[trace1],layout=layout)
plot(fig)