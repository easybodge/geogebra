<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>GeoGebra App</title>
    <script type="text/javascript" language="javascript" src="web/web.nocache.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <style>
    #ggbsplash {
    	z-index:100000;
    	position:absolute;
    	top:0;
    	left:0;
    	bottom:0;
    	right:0;
    	display:table-cell;
    }
    
    #ggbsplash div {
    	display:block;
    	margin: 17% auto;
    	background-color:white;
    	width:427px;
    	position:relative;
    }
    
    #ggbsplash div img.spinner {
    	position:absolute;
    	left:50%;
    	bottom:5px;
    	margin-left: -8px;
    }
    </style>
    <script>
	    var GGW_appengine = GGW_appengine || {};
	    GGW_appengine.FILE_IDS = <%= request.getAttribute("ids") %>;
	    GGW_appengine.CLIENT_ID = <%= request.getAttribute("client_id") %>;
	    GGW_appengine.USER_EMAIL = <%= request.getAttribute("email_address") %>;
  	</script>
</head>
<body data-param-app="true">
	<div id="ggbsplash">	
		<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAasAAAB4CAYAAABMzN1YAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAN1wAADdcBQiibeAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAACAASURBVHic7d15XFT1+gfwzwzD5jDsmygJggiCoAiWCpJKpphKaZlm17wp16VNMavrrbxtlmVZYZpLXrcsNAPJXK6RuyyyKCCjrNIoLsg+A8MMzO8Pf8x1mAPOCmfweb9e/jFnzpzzHTzzPOd8V45CobgJQgghhMU4CoVC0dOFIIQQQrrC7ekCEEIIIQ9CyYoQQgjrUbIihBDCepSsCCGEsB4lK0IIIaxHyYoQQgjrUbIihBDCepSsCCGEsB4lK0IIIaxHyYoQQgjrUbIihBDCepSsCCGEsB4lK0IIIaxHyYoQQgjrUbIihBDCepSsCCGEsB4lK0IIIaxHyYoQQgjrUbIihBDCepSsCCGEsB4lK0IIIaxHyYoQQgjrUbIihBDCepSsCCGEsB4lK0IIIaxHyYoQQgjrUbIihBDCepSsCCGEsB4lK0IIIaxHyYoQQgjrUbIihBDCeryeLgAhpkwsFqO6uhrV1dWQy+WwtbWFnZ0d7OzsYGlp2dPFY7WCggLU1dUpX9vZ2SEwMLAHS0TY7KFOVhRoiKZkMhmysrJw5coV1NTUKK8bmUzW6WcsLS1hZ2cHLy8vDB06FIGBgeDz+d1YanY7cuQIrl69qnzt5+dHyYp06qFIVhRoiK5EIhFOnz6NjIwMSCQSrT4rlUpx+/Zt3L59GxkZGeByufD29kZwcDCGDx8ONzc3I5WakN6nVycrCjREF1KpFBkZGTh9+jSuXbtmsOO2tbWhpKQEJSUlSEpKwqOPPorp06fD0dHRYOcgpLfqdcmquwLNY489hmnTplGg6WXKy8uxZcsWVFVVGfU8CoUCaWlpuHDhAsaPH4/JkyejT58+Rj0nIaasVyWr7gw058+fR2ZmJgWaXkKhUOD48eP49ddf0dra+sD9ra2t4eTkBCcnJzg7O8PJyQnW1taora1FTU0NamtrUV1djRs3bqCtra3T48jlchw7dgxnzpxBTEwMoqOjweFwDPnVCOkVekWyMlaguX79OhQKRafHoUDTOzQ2NmL79u3Iz8/vcj+BQIBRo0YhIiJC42rghoYG5ObmKttMO0tcEokE+/fvx9WrV/Hyyy/DyspK6+9BSG/GUXQVjU2AsQNNTk4OsrOzuww07YKDgynQmJji4mJs2bIFtbW1jO9zOBz4+/sjMjISISEh4PF0v78Ti8XIzMzE4cOHOz0fALi7u2PJkiW9vl103bp1ar0B4+Pje7BEhM1MOllRoCH6uHXrFj755BM0Nzczvu/u7o4FCxbA09PToOdtaWnB8ePHcfTo0U7PbW1tjQULFiAoKMig52YTSlZEG2arV69e3dOF0MWtW7ewbt06NDY2Mr7v7u6ON954A08++SQ8PDzA5eo3WYeFhQW8vLwQFRUFCwsLXLt2DXK5XG2/xsZGpKWloX///nB1ddXrnMR4pFIp1q9fj5qaGsb3IyMjsXjxYqN0oDEzM8OgQYMQGRkJmUyGiooKtepmuVyOzMxM8Pl8eHt7G7wMbHD+/HncvXtX+drJyQmjR4/uwRIRNjPJ6ZakUik2bdrU6V1pZGQkVq1aZfA7YuBe0oqJicHHH3+McePGMSbBpqYmJCQk4M8//zT4+Ylh7Nq1Czdu3FDb3qdPHyxatAhz586FhYWFUctgY2OD559/Hq+99hpjBx0+nw8fHx+jloEQU2GSyYoCDdHHn3/+iczMTLXtDg4OeO+99zB8+PBuLU9AQADeeecd9O3bV7nNzs4OK1aswCOPPNKtZSGErUwuWVGgMW2NjY04deoUNm3ahIMHDzLedBhTSUkJ9u3bp7adx+Nh0aJFcHBw6NbytHN1dcXbb7+NoUOHwsnJCW+++abKNUXIw86kuq6zPdBs3boVN27cwLJly+Di4tIjZWGz7du3Y9my5air+18HFQ6Hg0WLFmHt2rWwsbExehl++uknxuENs2fPhpeXl9HP3xUrKyssXboUYrG4W/4WYrEYRUVFyuEatbW1aGpqgp2dHezt7WFvbw8nJyf4+vrq1TnJ0CorK1FRUaEy1ITH4ynn9nR2dkZAQECPjX0sLi5GcXExbt26BZlMBnd3dwQFBel9fbVPTCASiVBXV4e6ujqIxWIIBAI4ODjA0dERzs7O8PHxgZmZmWG+DIuw5wrUAAWa/zG1QLN27Vq89dZbGDQoAvPmrYKHRyBqakQ4f34HNm7chLy8fJw8eULvjjBdKS0tRUVFhdr2yMhIREREGO282uBwOEa9fsRiMXJzc3HhwgUIhcIHDscA7vVMDAkJQVhYGAICAnrkeqqpqUFmZibS09MhEokeuL+ZmRn8/PwwfPhwjB49Gubm5nqd/+DBg7h48aLyNY/HwzvvvKOyz+XLl5GSkoLS0lK1z6ekpMDPzw/Lli3T+hq/fPkyMjMzcenSpU47lN1PIBAgPDwco0aN6rR259y5c/jjjz+6/D5s0/NRTEMUaEw30BQWFuJf/3oXoaEzEBe3Tzlw2sHBEwMHjoKXVzh27lyA5cuXY+HChbC3t4ejoyOsra0NWo4TJ06obevfvz+ef/55g56HjRQKBVJTU5GcnAypVKrVZ5uampCWloa0tDQ4OTlhzpw53dalXiqVIjk5GampqV0O0O+otbUVhYWFKCwsxOHDhxEbG4tHH31U50H71dXVKkmy4+8oJSUFv/32W5fH8PPz0ypRlZaW4sCBAygqKtKqrA0NDUhNTUVqaip8fX0xf/58ODs7q+xTX1/f5fdhI/aX8P9RoDG9QNMuOTkZMpkMs2dvYAwWY8a8jDNntmLv3p/Q1NQE4F7iHzBgAIYNG4aQkBB4eHjoVYaGhgZkZWWpbZ84caJJ/FD1IRKJsGvXLpSXl+t9rLt37+Lbb79FWFgYZs2aBVtbW/0L2ImCggLs2bNHpXu7LmpqarB9+3akpqYiLi5OLXDro62tDT/88ANjO/r93NzcMHnyZI2O2dLSgl27diEjI0Pv8hUXF+PDDz/ErFmzTH5YgEn8SinQmF6gadfU1ITff/8drq4DYWvb+UBpX99I/PFHJhQKBTgcDhQKBcrLy1FeXo6kpCQMHDgQM2fO1LmH5dmzZ9XGxdna2mLEiBE6Hc9U5OfnY+PGjYxjAu9nZWWFfv36wcHBAQ0NDcr2oJaWFsb9L1y4gMLCQixbtswoQ0SSkpJw+PBhxvcsLS3h4eEBZ2dnODs7QyAQoLq6GlVVVaiqqup0mrRr165hzZo1WLx4MXx9fQ1Szl9++YUxUbVP51ZeXo6WlhbMmTNHo1hVX1+PhISEB07CbW5urpwqDrgXI+vq6hgnLGhubsaOHTtQWFiI+fPnG7Wq3ZhMItI/rIEmLy8PmzZtMrlAA9y7O/ztt99w4sQJ3LlzB42NXd8dNzZWwcLCstNqmtLSUqxduxYjRozAc889B3t7e43LolAocOrUKbXtUVFRvfpmp7CwsNPrx9zcHOPHj4evry/69eunDHr3k8vlKCgoULaXdHyqF4vFWL9+PeLj4/V+8r1fcnIyY6Ly9PREVFQURo4c2eXiqHfv3sXJkydx5swZiMVilfcaGxvx1Vdf4aWXXkJ4eLhe5UxPT8fx48eVrzkcDh5//HHExMQobwTlcjkqKys1+p1VVlbi66+/7nSguqurK6KiohAWFgY7OzvG38rt27dx6dIlZGRkqCW8jIwMWFpaYu7cudp8TdZg/S/1YQ00BQUFJhlogHsNwnv27FHOfu/m5gahUIiCgiMIDJyktr9EUouCgsMYP34c3n33XdTW1qKsrAy5ublqjelZWVkoKSnBq6++iv79+2tUnuvXr6tVJfF4PIwdO1bHb8h+RUVF+O677xgXGA0ODsasWbMeWB3G4/EQEhKCkJAQ1NfX48CBA0hLS1N5amlsbMSXX36J+Ph4g3S1T0lJwe+//66yzcXFBS+99JLGT0NOTk545plnMHXqVBw7dgwpKSkqZZbL5di+fTscHBx0fsJqbW3Frl27lK8FAgGWLFmCgQMHquzH4/E0SlSNjY1ISEhgTFSenp6YMWMG/P39H9jm5urqiujoaERHR+PChQtISkrCnTt3lO+fPn0a9vb2Jhk7WT/d0vXr13HkyBGVbTweDy+//HKvXXpeKBTiu+++Y0xUwcHBeOWVVzBixAi4ubl12j2Xy+XC3d0doaGhGDNmDBobG3H9+nWVfVpaWpCdnY2hQ4dCIBDoXW6ZTIbdu3dj//79Kotd2tvb49q1Cly8eAgBARNVqgObmuqwffuLuH79Inbt2oWAgAC4urpi8ODBiIqKwqhRo9DS0oK//vpL+Znm5mZkZmbC29tbo/aHsrIytaqaoUOHsqZjjqE1NDRg3bp1yva/do6Ojpg/fz6mTp2qdbduS0tLDBs2DAEBAcjPz1e5+WlpaUFeXh4iIiK0CoIdp1uqq6uDUChU2Sc0NBSvvfaaTlOXtfcI9Pb2Rl5enkriVigUyM/PR3h4uEYdeZhunNo7OHG5XCxZsgSDBg3SuozAvcS3YcMGlWu83ejRo7F48WK4ublp3TnEw8MDERERuHXrFiorK5Xbr169CrFYjPr6euU2LpeLKVOm6FT+7sL6ykumxtXAwMBuaW/pCfX19fj+++/V7ogdHR2xZMkSLF26VOsGYltbW7z00kt48803YWdnp/JeQ0MDvv32W607bnQkkUiwfv16nDt3TmW7m5sbXnnlFRw5chgWFm1YsyYMmzbNQHLyv7Bjx3ysXu2Py5ePYN26dYzVMs7OznjxxRexatUqlW64TU1N2LBhA27fvv3Ast1/Z9nO3d1dh29pGvbu3YuGhgaVbQKBACtXrkRISIhex/bx8UF8fLxaNezdu3eRnJys17E73pzFxsbiH//4h96rGAQGBmLVqlWMPeI2bdqkVS9DJtOmTYO/v7/On29fGuZ+HA4HL7zwAubNm6dXt3sLCwvExcWpde5gSoxsx/pkxbSQYm8OND/++KPKUwnA/kBTU1ODtWvXori4WLnNzMwMMTExyllFwsPDcflyAeLiFqKhIQ+HD3+Cq1cPITJyJNLT0/H66693eQ5PT0+sWLFC2ZNRJpPhypUreP7557Fq1SokJyd3OgaF6RrqrYO2c3Jy1DojcblcxMXFGWzQvJubG+Lj49VqNlJTUw3SEQgARo4cqXHvOU04OzsjLi5O7cmvvLwcaWlpOh/X1tYWTz75pM6fv3nzJmNP5ylTphismprD4SA2NtbkaxJYn6yY7op7c6DJyclR2cb2QCMWi/H555+rVDPY2tpi5cqVmD59ukpwcHR0REJCAoqK7lVD3LlzGwcPJiM0NFSjc1laWmLp0qXgcrnYt28/jh07hhMnTmLNmk8RGxuLgIAhOHr0qNrnmJ7ODdl9mU0OHTqktm3mzJnw8/Mz6HlcXV0xdepUlW0KhYLx76+tvn37GqUTwIABA/D000+rbU9JSXlgJ6bOjB49Wq/edUlJSWrjJQcPHoynnnpK52N2Zvbs2SY9XynrkxUFGvYGmra2NmzevFnl/6hv3754++23HzijiK4Dfs+ePYvNmzdDIPDEsmXH8c03Dfj220YsX54KhcIOMTExOH36tMpnHpYnK6FQqFa94+bmhgkTJhjlfBMmTFDrnJObm9tpbzZN/f3vfzdae3R0dDQCAgJUtt29exdnzpzR+lgcDkevp5Vr166p3Zzy+XwsWLDAKCuO83g8/O1vfzPZruusLzUFGnYFmvz8fHzxxReYP38+ZsyYgcOHDyvvDPv164eVK1cy9k40BIVCgXnzXoKjoyfefvs8/P0nwMKiD8zNrTF48DisXHkWjo6emDfvJZV2CKbv1LHtrjdgWpLGmD0euVwunnjiCZVtbW1tOHv2rM7HHDhwoNEngGaqtrtw4YLWx3nkkUf0ikVM5xw7dqxR2+Pd3d3x6KOPGu34xsT6/osUaIyjPdDs2LFDua090DBVQchkMrz33nv4/PPP0draCjs7dzQ23kVrqwwuLq6YPHkSlixZYtTJQ4VCIcrKSvG3v22FlZX6D9rKyhYxMe9i584FEAqFyjtoKysrtZ5xNTU1Rl8cMz09XaO53DTl7u6OwMBAxvfa2trUetKZm5tj1KhRBjs/k+HDh2PPnj0q1WhCoVDnaqzIyEhDFa1T/v7+cHNzw61bt5TbSkpK0NTUpNUTv77XT15ensprDofTLd9/0qRJOH/+vNHPY2isT1YUaIxHm0Dz1ltv4auvvkJkZBymTfsAtrZukMtbcPFiMvbuXYpDh37Hl19+adTytk8kOmBAWKf7tL938eJFZbISCARqNz137twx+jV07NgxjSZd1dS4ceM6vYauXbumthjpiBEjwOfzDXZ+JtbW1ggKCkJubq5yW3l5OVpbW7We+dvS0hJhYZ3/3xoKh8NBVFQUEhMTldvaf4PaLDGkz/VTVVWl0s4LAEFBQUarlbifu7s7nJ2dGWut2Iz1yYoCjfFoGmhyc3Oxfv16REUtxpw53ym383gWGDHiWbi5Dcann47EO++8g82bNxutvO3VQ9XVFejfn7lnZHV1hcq+ABjHkDF13GG7riZJZprstLi4GJ9++qkxiwQAalP8yGQyiEQiDBgwQKvj9O3b1+iLprZj+i2WlZVplaz0qQJkmpRb396+2ggKCmLshchmJpGsOqJAYziaBJrjx49DoVBg+vQPGY/Rv38wRox4DocO/c74vqGEhobCysoap09vRnDwVMZ9zpzZAisra5Uehr3lGuqqLYOpurx9rryeUF1drXWy6s716JieYO4fJKsJfZJVXV2dQY+nLU1nf2ETSlbdxJQDTV5eHhwcPMDnd15F0a/fUKSl7UJNTY3Rgo6VlRU++uhDrFixAj/99CpmzFgLc/N7bQxyuRRJSatw8WIKvvjiC5WBpEw/zJs3bxqljMbU1Q1PxznwepouVeiOjo5GKAkzc3Nz2NraqiQobZOVPu2zTOfqjirAdqY4qQLrkxUFmu7XMdD4+PigtnY3mpsbYGXFPC3T7dtFcHBwNPrd8fLly3HlyhVs2ZKA7Ox98PJ6DABQVpaG+vpbWLhwIZYvX67yGabZBYRCIerr6436o12yZAnj3Hya+OGHH9QmIu2qrIZsXzWEjjNoaKK7O045Ojrqlaz0mVmi49+Hw+F0a7I2xPRq3Y31yYoCTffr+EOKjIyEQtGG1NRvEBOzSm3/mhoRsrP34YknHjd62TgcDjZv3oyZM2ciISEBZ87cG1Pl6uqIb775DPPmzVP7TP/+/SEQCFS+l1wux8mTJ9XGmxmSrnfKra2tao3vXC63ywlRO87MwOPxsHr16h4bU6PLOLqO7bbG1rHjlraTu+ozGWzHKaQUCgVkMlm3LUev6yDonsT6ZPUwBhpLS0u8//77Op3fEDoGmnHjxiE2Nha//fZv8PmOGDt2kXLQYmVlIX744QW0tkqxdu3abivjxIkTwefz0bfvTuW2zrpLczgcDB48WG1cy8mTJzF58mTWzUBdUVGhtryLt7d3lwNlO94MyeVyyOVyg8yE3l10eRrTlUKhUJtwQNubX32uG6ZzVVVVdVtbUnV1dbecx5DY9StlYGqB5tq1a3oHGqlUCoFA0G09ozSxbds2zJnzAn78cQl+++3f8PQcjvr6G7h+vQAODg5ITPxZ51mnddXelR24N/K/qyrIkSNHql1DDQ0NyMzMNPoQAW3l5+erbes460JHTMFPJBKZVLLSd+YLbdTW1qo9XWhbDalPNSAlK+2xfgYL4F6g6ag90LBNQUGB2jZdAo229efG5ujoiL17f8SECRPg6irArVsX4OFhjX/+8x0UFl426lMuk5aWFly+fFn5OiQkpMsqr+DgYMZ1u44fP642N1tPksvljOu3PWhWb6YpyExtZu3i4mK0trZ2y7mY/jbathnpc6PM9P/VcQkfY6JkZSQUaNhBIpHAx8cH48ePxzPPPIPvv/8eH374YY9Mf5Wdna3Spjhs2LAu9+dwOIyzeItEIvz6668GL5+usrKy1G5UbGxs4O3t3eXnmG6IDDlOsDs0NzejtLS0W87FNCXUkCFDNP48h8PRK1l5e3urtVudO3dO7+VKNNHW1qZSK2EqTCJZPYyBhukJrad1fALsqbszuVyOlJQU5Wtra2uNAk1YWBjjjcGxY8fUltXoCS0tLYyTGUdHRz8wMDo4OKhV+f3111/dEvzu3r1rsPOkp6cb5Dhdqa2txaVLl1S22draajUnob4dIczMzNSu2aqqqm753V+8eJF1NTeaMIlkBTx8gSY/P591gUYgEKi0vfXUOLCTJ0+qnHvixIkatR9wuVzMmTOHcUbrHTt24MaNGwYtp7b27dunMl8dcK8tbty4cRp9fujQoSqv6+vrjV5VXlVVhY8++ggfffSR2lx3ujh79qzRx1GePn1arUYmKCjIKDOdd6Xj/xfAPEeooXVclcBUmEyyetgCTU1NDSsDzf1VfvcvtthdRCIRkpKSlK/t7e0RHR2t8ecDAwMZZ7KXSqXYuHGjWnfm7pKTk8NYhfzEE09ovFLu+PHj1e74U1JSjFZVLpPJsHHjRkgkEohEIiQkJGDt2rWMy/poqq2tTeX/19Bu3LihthQOh8PB+PHjjXbOzoSHh6t16sjPzzdqFZ1QKFRp6zUlJpOsAAo0hqRroLk/qZaXl6t10zeUoqIizJs3DwEBQ2BnZ4/Ro8fg/fffR0JCgkpvy2nTpmnda/Lpp59mrPK5ffs2Pvvss25v67l06RK2bt2qtt3e3l7jmx3g3hN6x56Nt2/fZnziN4Tdu3er/a0aGxv1Htx74cIFozxhSKVSfP/992rjJ8PDw7scWmIs5ubmiImJUdu+Y8cOo/SMrK2txdatW7ulxsYYTCpZARRoDEXXQDN69GiV18YIKjt37kRwcAj270+CldVghIa+gDt3uPjggw+wZctWZX378OHD1cqjCR6PhwULFjBOFFxZWYlPP/20W6pjgHsdRTZt2qTWjZrH42HRokUa3+y0mzRpklq186FDh9TaaPR18uRJxuXg58yZY5DhJImJiWorEehDoVBgz549arPfmJmZYfr06QY7j7YiIiLUxnSKxWJs27bNoDeqbW1t2Lp1a7eOZTM0s9WrV6/u6UJog8vlYvDgwcjIyFC7Q2psbMT58+dhbW39wE4NhpCdnY3Nmzerdbfl8Xh47bXXtO4l5+HhgVOnTqlcpEVFRXjkkUfg5uZmkDID9wLN4cOH1bbHxcU9cDZ7Pp+PK1euKDtXVFRUYNCgQXByckJGRgZ+/fVX/PHHH5BIJHB3d9f6qScvLw9Tp06Dt/covPFGKiIjF2Lo0CkYM+bvGDQoEunpP+P69b8QFRWFV199VeexLjY2NggODkZubq7azAltbW3Iz89HRUUFhgwZYpTxbjKZDPv27cMvv/zCGJTmzp2L4OBgrY/L5/NhZWWl1lCfl5cHLy8vg/TcLCwsxPbt29XKHR4ejokTJ2p8nPPnz6s8yd9fxa9QKJCVlQUXFxf069dPr/JKJBJs2bIF2dnZau8999xzna6GcL/c3FyVmzsul4spU6boVa7243h6eiI9PV3liae6uholJSUICgrSe9XkhoYGbNq0CVeuXOmyHIb4PsZkcskKoECjD0MEGicnJ5W76rNnz+Kjjz7GBx/8G4cPH0Zqaip2796Nbdt+gK+vzwPHmd1v4cKFuHmzFvHxJyEQqCZOZ+eBcHHxwalT/8Hs2bPx2GOPaXxcJgKBAKGhocjLy2Ocn/HWrVtIS0uDXC6Hi4uL1k85nREKhdiwYQPj4F/g3owhTNVDmho4cCAqKipU2lDlcjkyMjJgZ2en9Wzo7e7evYs9e/bgwIEDatdP3759sWjRIq1uHjomq0GDBsHLy0tZtdzW1oacnBzI5XL4+Pjo1ANPJBLhq6++QllZmdp7ERERiI2N1eg4xkpWwL3fE5/PV7seqqqqkJmZiYEDB+o852ZRURHWr1+vVnZfX1+V3ryUrIyIAo12DBlonJ2dIZFIUFZWBolEgr17f0JVVR1mz96A2bMTEBPzLwQEROPq1fPYsuUbDBs27IFjzdq98sqrGDLkKYSFzWJ8393dH8ePfwEvrwGYNGmSRsfsSp8+fRAWFobCwkLG7rxSqRRXrlxBamoqysvLYW5uDldXV63n3JPJZEhPT8eOHTtw9OjRTicvnjBhAp599lm9e6YNGTIE2dnZkEgkym0KhQKXLl3C1atX0a9fP9jb22t0LKlUikOHDmHbtm2M1ex2dnZYvny51m1VHZOVs7MzFi9ejOLiYpXtxcXFOHfuHKytreHh4aHR376kpARJSUlITExknHtz0KBBiIuL0/j/0ZjJCrg37qqmpkZtfGVzczPS0tJQU1MDJycnjaeEKi8vR3JyMvbt26fSls/lcrFw4ULw+XyValZTSFYcham2tv2/+vp6fP311122VXG5XAQGBmLMmDEIDg7W+g5NJpMhMzMTqampXQ7WnTBhAmbOnKn35KGNjY1Ys2YNY9dwPz8/zJw5U+OkJZVKceTIEfz3v/9lnJjXzs4Ob731ltZzIcpkMnzyySfYvn07bty4hVWrstG3r+oTlFwuxZo1I9HcXIni4qIHBrPm5mbw+Xw8+eRbiI39pNP9Vq3yQnT0KOzdu1erMndFIpFg586dyMnJeeC+AoEAAQEBcHJygqOjo8o/LpcLsVgMsViMuro6lJaWoqioCKWlpV1OjGxubo65c+fq/bR4v9raWnz99deMPWU5HA58fHwQEhKCIUOGwMHBAX369EFbWxtu3rwJkUiE69evQyQSoby8vNPkamNjgzfeeEOnDgrr1q3D1atXla/9/PwQHx+PpqYmfPHFF4y/aUtLSwwZMgSBgYFwcHCAra0tLCwsUF1drVxWp6CgoMt4EBISggULFmhV6/Kf//xHZSl4Ho+HDRs2aPx5TSgUCuzfvx/Hjx/vdB8/Pz+MHDkSbm5ucHZ2hoODA1pbW5Xfv7KyEufPn2eMgISN9gAABc1JREFUU+2JKjQ0FEeOHFEZo2qM72NoJp+sAAo0PRFogHsdQDw8PDB+/BuYOfMLxn0KC49j/foncPDgwS6nZBKLxcjKysLMmc/CxWUYXn/9KON+dXWVWLnSAx9//DH++c9/6lTurgiFQvz888/dOhTC0dERixYt0vmpuStisRjffvstYzVYR1wuFxwOR+Mpj3x8fLBw4UKdq6g6S1bt5d65c6fKKtaGEB0djZkzZ2r95Nodyard6dOn8eOPP2rUwcLMzAxtbW0P7OFnb2+PF198EUFBQQBgksmKXbPA6qhPnz5YtGiRRoGmoaEBGRkZBju3sQKNvb09VqxYwRhoFAoFiouLUVxcjF9++QVA9wca4F7PudbWVnh5hXe6T/t7x44dQ1hYGKysrCCRSCCRSNDQ0IDS0lIIhUKIRCIoFAp4evZHdvYx5Of/jqAg9erUn39+HTwer9MZ1vXl7++Pd999F6dOncLBgweNutaYra0tJk2ahLFjx+o1KWpX+Hw+4uPjcezYMRw9ehRSqbTTfTXtfcbj8TBhwgRMnz7daEta8Pl8LF68GGfOnEFiYmKX5daEs7MzZsyYobKCNFtFRkbC09MTiYmJKCkp6XJfTX7vERERmDFjhl6LRbJBr0hW7SjQPJghA017D8W6us4Xw6yvv/fepUuXoEnzaGhoKK5dq8DGjU/jqafeR3j487C37weR6BIOHfoAly79htWrV+vUgUVTXC4Xjz/+OEaOHIm0tDRcvHgRRUVFBptk1d7eHhMnTjTqtXM/c3NzTJkyBWPGjEFSUhIyMzN1Ws/I3NwckZGRePLJJzVu79JXREQE/Pz8cOTIEWRlZWm95pWNjQ1iYmIQFRXFuhUauuLl5YWVK1ciJycHSUlJWi84y+VyERAQgIkTJ2rcXsx2vaIakIlEIukVgaZdbW0tKwPNI48MgLl5X6xceQ4cjnpbXXLyu/j994/w7LPPavQUZ2dnh+HDhyMxMfG+mQw4ABTo04ePzz77FEuXLu32qXGamppQUFCAixcvoqCgQKsbIUtLSwwaNAj+/v4ICAhAv379ur3895NKpRAKhcjPz1cOQ2Cq5uZwOHBxcVGW29/f36B350KhUGXcj0Ag6DKwtrS0ICcnB+np6aisrERtba3aTZqZmRlcXFwQFBSEkJAQ+Pr6GmQByrKyMpU2ZC6XixEjRuh9XE2JRCLk5uYiNzcXlZWVajGAw+HAxsYGrq6uGDFiBMLDw7vsjFFZWanWYaQ7v48uem2yuh8FGsMHmnaJiYmYNWsWxo79B5599ktYWPzvHOfO/Qe7dy+Er68PoqKiGD/P4/EwcOBA+Pn5wc/PD76+vsonvpycHGRmZuLGjRsIDAxEZGQk3N3dDf4ddNHc3Iz6+nqVfw0NDeDxeLCxsYGNjQ0EAgFsbGzg4uLSbSvA6qqpqQn19fVobGyEtbW1suw9ea0/SFtbG+rq6lBbWwtLS0vY2tqCz+ezusyGIpFIUFdXh6amJtja2sLe3t6knhx18VAkq47aA01DQ4NKsLk/0LQHGwo0D/bmm29i3bp1sLfvC1/fKFhZ2aCsLB0i0SWMHj0aBw4cgEwmQ3V1NZqamtCnTx/l39jBwaHX/8gIIfp7KJMVMbwTJ07gm2++QVZWDsTiRgwbFoLY2FgsWbLEINUwhJCHGyUrQgghrEe3vIQQQliPkhUhhBDWo2RFCCGE9ShZEUIIYT1KVoQQQliPkhUhhBDWo2RFCCGE9ShZEUIIYT1KVoQQQliPkhUhhBDWo2RFCCGE9ShZEUIIYT1KVoQQQliPkhUhhBDWo2RFCCGE9ShZEUIIYT1KVoQQQliPkhUhhBDWo2RFCCGE9ShZEUIIYT1KVoQQQliPkhUhhBDWo2RFCCGE9ShZEUIIYT1KVoQQQliPkhUhhBDWo2RFCCGE9ShZEUIIYT1KVoQQQliPkhUhhBDWo2RFCCGE9ShZEUIIYT1KVoQQQljv/wBQGOi5ccV4vQAAAABJRU5ErkJggg==">
		<img width="16" class="spinner" height="16" title="" alt="" src="data:image/gif;base64,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" />
	</div>
	
    <article class="geogebraweb"></article>
 <!-- Google Analytics (Start) -->
<script type="text/javascript">

var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-1780044-1']);
_gaq.push(['_setDomainName', 'geogebra.org']);
_gaq.push(['_setAllowLinker', true]);
_gaq.push(['_trackPageview']);
_gaq.push(['_trackEvent', 'Startup', 'Application', 'GeoGebraWeb']);

(function() {
var ga = document.createElement('script'); ga.type = 'text/javascript';
ga.async = true;
ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
var s = document.getElementsByTagName('script')[0];
s.parentNode.insertBefore(ga, s);
})();

</script> 
 <!-- Google Analytics (End) -->
</body>
</html>
