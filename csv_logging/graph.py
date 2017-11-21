from plotly import __version__
from plotly.offline import download_plotlyjs, init_notebook_mode, plot, iplot
import plotly.plotly as py
import plotly.graph_objs as go
import plotly.figure_factory as FF

import numpy as np
import pandas as pd

lf = pd.read_csv('Pathleft.csv')
rf = pd.read_csv('Pathright.csv')

logl = pd.read_csv('logPathleft.csv')
logr = pd.read_csv('logPathright.csv')

lptrace = go.Scatter(x=lf['DeltaTime'], y=lf['Position'],mode='lines', name='Position (in)')
lvtrace = go.Scatter(x=lf['DeltaTime'], y=lf['Velocity'],mode='lines', name='Velocity (in/s)')
latrace = go.Scatter(x=lf['DeltaTime'], y=lf['Acceleration'],mode='lines', name='Acceleration (in/s^2)')

loglptrace = go.Scatter(x=logl['DeltaTime'], y=logl['Position'],mode='lines', name='Log Position (in)')
loglvtrace = go.Scatter(x=logl['DeltaTime'], y=logl['Velocity'],mode='lines', name='Log Velocity (in/s)')
loglatrace = go.Scatter(x=logl['DeltaTime'], y=logl['Acceleration'],mode='lines', name='Log Acceleration (in/s^2)')

rptrace = go.Scatter(x=rf['DeltaTime'], y=rf['Position'],mode='lines', name='Position (in)')
rvtrace = go.Scatter(x=rf['DeltaTime'], y=rf['Velocity'],mode='lines', name='Velocity (in/s)')
ratrace = go.Scatter(x=rf['DeltaTime'], y=rf['Acceleration'],mode='lines', name='Acceleration (in/s^2)')

logrptrace = go.Scatter(x=logr['DeltaTime'], y=logr['Position'],mode='lines', name='Log Position (in)')
logrvtrace = go.Scatter(x=logr['DeltaTime'], y=logr['Velocity'],mode='lines', name='Log Velocity (in/s)')
logratrace = go.Scatter(x=logr['DeltaTime'], y=logr['Acceleration'],mode='lines', name='Log Acceleration (in/s^2)')



lpathtrace = go.Scatter(x=lf['X'], y=lf['Y'], mode='lines', name='Left Path')
rpathtrace = go.Scatter(x=rf['X'], y=rf['Y'], mode='lines', name='Right Path')
mpathtrace = go.Scatter(x=(lf['X'] + rf['X']) / 2, y=(lf['Y'] + rf['Y']) / 2, mode='lines', name='Robot Center Path')


llayout = go.Layout(title='Left Motion Profile', plot_bgcolor='rgb(230, 230,230)')
rlayout = go.Layout(title='Right Motion Profile', plot_bgcolor='rgb(230, 230,230)')
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

lprofilefig = go.Figure(data=[lptrace,lvtrace,latrace], layout=llayout)
rprofilefig = go.Figure(data=[rptrace,rvtrace,ratrace], layout=rlayout)

pathfig = go.Figure(data=[lpathtrace,rpathtrace,mpathtrace], layout=pathlayout)

# Plot data in the notebook
plot(lprofilefig, filename='Left Motion Profile.html')
plot(rprofilefig, filename='Right Motion Profile.html')
plot(pathfig, filename='Robot Path.html')