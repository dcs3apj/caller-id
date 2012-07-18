<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
  <meta charset="utf-8">
  <title>It Fits</title>
  <meta name="HandheldFriendly" content="True" />
  <meta name="MobileOptimized" content="320" />
  <meta content="minimum-scale=1.0, width=device-width, maximum-scale=1.0, user-scalable=no" name="viewport" />
  <meta name="description" content="">
  <link  href="http://fonts.googleapis.com/css?family=Oswald:regular" rel="stylesheet" type="text/css" >
  <link href='http://fonts.googleapis.com/css?family=Junge' rel='stylesheet' type='text/css'>
  
  <c:url value="/assets/css/style.css" var="url"/>
  <link rel="stylesheet" href="${url}">
  <c:url value="/assets/fonts/raphaelicons.css" var="url"/>
  <link rel="stylesheet" href="${url}">
  <c:url value="/assets/css/main.css" var="url"/>
  <link rel="stylesheet" href="${url}">
  <c:url value="/assets/js/libs/modernizr-2.5.2.min.js" var="url"/>
  <script src="${url}"></script>
</head>
<!--[if lt IE 7]> <body class="ie6 oldies"> <![endif]-->
<!--[if IE 7]>    <body class="ie7 oldies"> <![endif]-->
<!--[if IE 8]>    <body class="ie8 oldies"> <![endif]-->
<!--[if gt IE 8]><!--><body><!--<![endif]-->

<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
  <header class="clearfix">
      <div class="container">
        <!--
        <a id="logo" href="index.html">It Fits</a>
        <ul class="social-icons">
          <li><a href="http://www.facebook.com/blog.cssjuntion" class="icon flip">^</a></li>
          <li><a href="" class="icon">T</a></li>
          <li><a href="http://www.twitter.com/cssjunction" class="icon">^</a></li>
        </ul>
        -->
        <nav class="clearfix">
          <ul role="navigation">
            <li>
              <a href="index.html" class="activePage"><span class="icon">S</span>Home</a>
            </li>
            <li>
              <a href="portfolio.html"><span class="icon">Û</span>Portfolio</a>
            </li>
            <li>
              <a href="page.html"><span class="icon">E</span>Page</a>
            </li>
            <li>
              <a href="blog.html"><span class="icon">E</span>Blog</a>
            </li>
            <li>
              <a href="contact.html"><span class="icon">M</span>Contact us</a>
            </li>
          </ul>
        </nav>
      </div>
  </header>
   <section role="banner">
       <hgroup>
           <h1>An HTML5/CSS3 powered website that contains very less images</h1>
           <!-- h2>Even the icons used are texts icons, easier to use/customize and load page faster.</h2 -->
       </hgroup>
       <article role="main" class="clearfix">
           <div class="post">
             <h2>It Fits: an HTML5/CSS3 Responsive template</h2>
             <p>Nunc porta, mauris sit amet laoreet vestibulum, elit leo iaculis lorem, mattis euismod eros lorem sed est. Donec porttitor augue vel nisl blandit gravida. Etiam eu purus non enim vehicula ultrices a non neque.</p>
             <p>Morbi purus odio, faucibus sit amet elementum in:</p>
              <ul>
                <li>interdum eu ipsum.</li>
                <li>Donec cursus pellentesque mauris vitae ultricies.</li>
                <li>Donec lacinia nunc in nisl hendrerit scelerisque.</li>
              </ul>
              <a href="page.html" class="button left">Learn more <span class="icon">:</span></a>
           </div>
           <aside role="complementary">
               <a href="#demo-url"><img src="http://lorempixel.com/420/290/nature/" alt="Lorem ipsum dolor..."></a>
           </aside>
       </article>
   </section> <!-- // banner ends -->
   <section class="container">
       <div class="columns">
           <article>
             <figure>
                <img src="http://lorempixel.com/210/140/technics/" alt="Lorem ipsum dolor...">
                 <figcaption>
                 <h3>Micro-formatted HTML5</h3>
                   <p>Pellentesque mi arcu, sollicitudin vel malesu, <a href="#">volutpat vitae nisi.</a> Nulla luctus lacus at tortor blandit semper. Donec gravida aliquet urna, eu pulvinar ligula pellentesque ornare. <a href="#" class="more-link">Read more &rarr;</a></p>
                 </figcaption>
             </figure>
           </article>
           <article>
             <figure>
                <img src="http://lorempixel.com/210/140/sports/" alt="Lorem ipsum dolor..." class="alt-col">
                 <figcaption>
                     <h3>Responsive</h3>
                   <p>Pellentesque mi arcu, sollicitudin vel malesu, volutpat vitae nisi. Nulla luctus lacus at tortor blandit semper. Donec gravida aliquet urna, eu pulvinar ligula pellentesque ornare. <a href="#" class="more-link">Read more &rarr;</a></p>
                 </figcaption>
             </figure>
           </article>
           <article>
             <figure>
                <img src="http://lorempixel.com/210/140/people/" alt="Lorem ipsum dolor...">
                 <figcaption>
                   <h3>Pure CSS3</h3>
                   <p>Pellentesque mi arcu, sollicitudin vel malesu, <strong>volutpat vitae nisi. Nulla luctus</strong> lacus at tortor blandit semper. Donec gravida aliquet urna, eu pulvinar ligula pellentesque ornare. <a href="#" class="more-link">Read more &rarr;</a></p>
                 </figcaption>
             </figure>
           </article>
           <article>
             <figure>
                <img src="http://lorempixel.com/210/140/nightlife/" alt="Lorem ipsum dolor..." class="alt-col">
                 <figcaption>
                    <h3>Browser compatible</h3>
                   <p>Pellentesque mi arcu, sollicitudin vel malesu, volutpat vitae nisi. Nulla luctus lacus at tortor blandit semper. Donec gravida aliquet urna, eu pulvinar ligula pellentesque ornare. <a href="#" class="more-link">Read more &rarr;</a></p>
                 </figcaption>
             </figure>
           </article>
       </div> <!-- //.columns -->
       <ul class="thumb-rotator">
         <li><a href=""><img src="http://flickholdr.com/165/65/logo" alt="" ></a></li>
         <li><a href=""><img src="http://flickholdr.com/165/65/brand" alt="" ></a></li>
         <li><a href=""><img src="http://flickholdr.com/165/65/apple" alt="" ></a></li>
         <li><a href=""><img src="http://flickholdr.com/165/65/nike" alt="" ></a></li>
         <li><a href=""><img src="http://flickholdr.com/165/65/levi" alt="" ></a></li>
       </ul>
   </section>
  <footer role="contentinfo">
      <p>
        <span class="left">CSS Junction &copy; - 2012 | Released under Creative Common License. <a href="#">Goto Top</a></span>
        <a href="index.html">HOME</a> | <a href="portfolio.html">PORTFOLIO</a> | <a href="page.html">PAGE</a> |  <a href="blog.html">BLOG</a> | <a href="contact.html">CONTACT US</a>
      </p>
  </footer>
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <c:url value="/assets/js/libs/jquery-1.7.1.min.js" var="url"/>
  <script>window.jQuery || document.write('<script src="${url}"><\/script>')</script>
  <c:url value="/assets/js/script.js" var="url"/>
  <script src="${url}"></script>
</body>
</html>